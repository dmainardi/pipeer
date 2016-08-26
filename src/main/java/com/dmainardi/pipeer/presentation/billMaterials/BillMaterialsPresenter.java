/*
 * Copyright (C) 2016 Davide Mainardi <ingmainardi at live.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dmainardi.pipeer.presentation.billMaterials;

import com.dmainardi.pipeer.business.billMaterials.boundary.BillMaterialsService;
import com.dmainardi.pipeer.business.billMaterials.entity.BillMaterials;
import com.dmainardi.pipeer.business.billMaterials.entity.GroupNode;
import com.dmainardi.pipeer.business.billMaterials.entity.ItemNode;
import com.dmainardi.pipeer.business.billMaterials.entity.Node;
import com.dmainardi.pipeer.business.billMaterials.entity.ProcessNode;
import com.dmainardi.pipeer.business.customerSupplier.boundary.CustomerSupplierService;
import com.dmainardi.pipeer.business.customerSupplier.entity.CustomerSupplier;
import com.dmainardi.pipeer.business.item.entity.Item;
import com.dmainardi.pipeer.business.workshop.entity.Process;
import com.dmainardi.pipeer.presentation.ExceptionUtility;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Davide Mainardi <ingmainardi at live.com>
 */
@Named
@FlowScoped("billMaterialsF")
public class BillMaterialsPresenter implements Serializable {

    @Inject
    BillMaterialsService service;
    @Inject
    CustomerSupplierService customerService;
    
    public enum NodeType {
        GROUP_NODE,
        PROCESS_NODE,
        ITEM_NODE
    }
    
    private CustomerSupplier customer;

    private BillMaterials billMaterials;
    private Long id;
    private Node node;
    private TreeNode root;
    private TreeNode selectedNode;
    private Process selectedProcess;
    private Item selectedItem;

    public String saveBillMaterials() {
        try {
            service.saveBillMaterials(billMaterials);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ExceptionUtility.unwrap(e.getCausedByException()).getLocalizedMessage()));
            return null;
        }

        return "exitFlow";
    }

    public void detailBillMaterials() {
        if (id != null) {
            customer = null;
            if (id == 0)
                billMaterials = new BillMaterials();
            else {
                billMaterials = service.readBillMaterials(id);
                customer = billMaterials.getPlant().getCustomerSupplier();
            }
            id = null;
        }
    }
    
    private void populateTree() {
        root = new DefaultTreeNode("group", billMaterials.getRoot(), null);
        if (billMaterials.getRoot().getChildren() != null) {
            for (Node nodeTemp : billMaterials.getRoot().getChildren()) {
                populateTreeNodes(root, nodeTemp);
            }
        }
    }
    
    private void populateTreeNodes(TreeNode parent, Node current) {
        String nodeType = "unknown";
        if (current instanceof GroupNode) {
            nodeType = "grp";
        }
        if (current instanceof ItemNode) {
            nodeType = "itm";
        }
        if (current instanceof ProcessNode) {
            nodeType = "prc";
        }

        TreeNode nodeTemp = new DefaultTreeNode(nodeType, current, parent);

        if (current.getChildren() != null) {
            for (Node node : current.getChildren()) {
                populateTreeNodes(nodeTemp, node);
            }
        }
    }
    
    public void deleteSelectedNode() {
        if (selectedNode != null) {
            service.deleteNode((Node) selectedNode.getData(), billMaterials.getRoot());
            selectedNode.getChildren().clear();
            selectedNode.getParent().getChildren().remove(selectedNode);
            selectedNode.setParent(null);

            selectedNode = null;
        }
    }
    
    public String addNode(NodeType nodeType) {
        switch (nodeType) {
            case ITEM_NODE:
                node = new ItemNode();
                return "itemNode?faces-redirect=true";
            case GROUP_NODE:
                node = new GroupNode();
                return "groupNode?faces-redirect=true";
            case PROCESS_NODE:
                node = new ProcessNode();
                return "processNode?faces-redirect=true";
            default:
                return null;
        }
    }
    
    public String detailSelectedNode() {
        if (selectedNode != null) {
            switch (selectedNode.getType()) {
                case "itm":
                    node = (ItemNode) selectedNode.getData();
                    selectedItem = ((ItemNode) node).getItem();
                    return "itemNode?faces-redirect=true";
                case "grp":
                    node = (GroupNode) selectedNode.getData();
                    return "groupNode?faces-redirect=true";
                case "prc":
                    node = (ProcessNode) selectedNode.getData();
                    selectedProcess = ((ProcessNode) node).getProcess();
                    return "processNode?faces-redirect=true";
                default:
                    return null;
            }
        }
        return null;
    }
    
    public String saveNode() {
        selectedItem = null;
        selectedProcess = null;
        if (node.getFather() == null)
            insertIntoTree();
        
        return "billMaterials?faces-redirect=true";
    }

    private void insertIntoTree() {
        TreeNode father;
        if (selectedNode == null) {
            //root will be the father
            node.setFather(billMaterials.getRoot());
            billMaterials.getRoot().getChildren().add(node);
            father = root;
        } else if (!selectedNode.getType().equalsIgnoreCase("grp")) {
            //selectedNode's father will be the father
            node.setFather(((Node) selectedNode.getData()).getFather());
            ((Node) selectedNode.getData()).getFather().getChildren().add(node);
            father = selectedNode.getParent();
        } else {
            //selectedNode will be the father
            node.setFather((Node) selectedNode.getData());
            ((Node) selectedNode.getData()).getChildren().add(node);
            father = selectedNode;
        }
        populateTreeNodes(father, node);
    }
    
    public void onProcessSelect(SelectEvent event) {
        ((ProcessNode) node).setProcess((Process) event.getObject());
    }

    public void onItemSelect(SelectEvent event) {
        Item element = (Item) event.getObject();
        ((ItemNode) node).setItem(element);
        node.setPrice(new BigDecimal(element.getStandardCost().doubleValue()));
    }

    public BillMaterials getBillMaterials() {
        return billMaterials;
    }

    public void setBillMaterials(BillMaterials billMaterials) {
        this.billMaterials = billMaterials;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerSupplier getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerSupplier customer) {
        this.customer = customer;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public TreeNode getRoot() {
        populateTree();
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Process getSelectedProcess() {
        return selectedProcess;
    }

    public void setSelectedProcess(Process selectedProcess) {
        this.selectedProcess = selectedProcess;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }
    
}
