INSERT INTO group_app(group_name) VALUES('user')
INSERT INTO group_app(group_name) VALUES('admin')

INSERT INTO user_app(user_name, pass_word, version) VALUES('maina', 'PuYC2SlAA74yPJE0eporJcAYhJfayYF+eqstExAB7ws=', 0)
INSERT INTO user_app(user_name, pass_word, version) VALUES('guest', 'pFCQ5Wt2OcrjZe5VPTIf6O92MArcq4daBkuz8Ku8CNA=', 0)

INSERT INTO users_groups_app(user_name, groups_group_name) VALUES('maina', 'user')
INSERT INTO users_groups_app(user_name, groups_group_name) VALUES('maina', 'admin')
INSERT INTO users_groups_app(user_name, groups_group_name) VALUES('guest', 'user')

INSERT INTO bank(id, iban, branch, version) VALUES (1001, 'IT00A0000000000000000000000', 'Intesa San Paolo - Pinerolo Piazza San Donato', 0)
INSERT INTO bank(id, iban, branch, version) VALUES (1002, 'IT00A0000000000000000000001', 'Unicredit - Torino Piazza Pitagora', 0)
INSERT INTO bank(id, iban, branch, version) VALUES (1003, 'IT00A0000000000000000000002', 'Banca del Piemonte - Saluzzo Via Roma', 0)
INSERT INTO bank(id, iban, branch, version) VALUES (1004, 'IT00A0000000000000000000003', 'Mediolanum - Milano via Monte Napoleone', 0)

INSERT INTO deliverymethod(id, name, version) VALUES (1001, 'Presso ns. stabilimento', 0)
INSERT INTO deliverymethod(id, name, version) VALUES (1002, 'Presso cantiere Apple a Cupertino', 0)
INSERT INTO deliverymethod(id, name, version) VALUES (1003, 'Presso sede Mediobanca a Milano', 0)

INSERT INTO paymentmethod(id, name, days, version) VALUES (1001, 'BB 120 DFFM', 120, 0)
INSERT INTO paymentmethod(id, name, days, version) VALUES (1002, 'BB 60 DFFM', 60, 0)
INSERT INTO paymentmethod(id, name, days, version) VALUES (1003, 'BB 90 DFFM', 90, 0)
INSERT INTO paymentmethod(id, name, days, version) VALUES (1004, 'BB 30 DFFM', 30, 0)
INSERT INTO paymentmethod(id, name, days, version) VALUES (1005, 'RIBA 120 DFFM', 120, 0)
INSERT INTO paymentmethod(id, name, days, version) VALUES (1006, 'RIBA 60 DFFM', 60, 0)
INSERT INTO paymentmethod(id, name, days, version) VALUES (1007, 'RIBA 90 DFFM', 90, 0)
INSERT INTO paymentmethod(id, name, days, version) VALUES (1008, 'RIBA 30 DFFM', 30, 0)

INSERT INTO unitMeasure(id, name, symbol, version) VALUES (1001, 'Meter', 'm', 0)
INSERT INTO unitMeasure(id, name, symbol, version) VALUES (1002, 'Piece', 'Pc.', 0)
INSERT INTO unitMeasure(id, name, symbol, version) VALUES (1003, 'Liter', 'l', 0)

INSERT INTO customerSupplier(id, businessName, name, isCustomer, isSupplier, version) VALUES (1001, 'mainardi', 'maina', 1, 1, 0)
INSERT INTO plant(id, customerSupplier_id, name, address, isHeadOffice, version) VALUES (1001, 1001, 'Headquarter', 'Piazza Roma, 1', 1, 0)
INSERT INTO plant(id, customerSupplier_id, name, address, isHeadOffice, version) VALUES (1002, 1001, 'Warehouse', 'Via Torino, 5', 0, 0)
INSERT INTO referee(id, customerSupplier_id, name, version) VALUES (1001, 1001, 'Maina', 0)
INSERT INTO referee(id, customerSupplier_id, name, version) VALUES (1002, 1001, 'Mainardi Davide', 0)

INSERT INTO tag(id, name, version) VALUES (1001, 'Inox', 0)
INSERT INTO tag(id, name, version) VALUES (1002, 'Steel', 0)
INSERT INTO tag(id, name, version) VALUES (1003, '40/40', 0)

INSERT INTO item(id, unitMeasure_id, code, name, version) VALUES (1001, 1002, 'p001', 'Pipe 3.14', 0)
INSERT INTO item(id, unitMeasure_id, code, name, version) VALUES (1002, 1002, 'p002', 'Pipe 4.15', 0)
INSERT INTO item(id, unitMeasure_id, code, name, version) VALUES (1003, 1002, 'p003', 'Pipe 5.16', 0)
INSERT INTO item(id, unitMeasure_id, code, name, version) VALUES (1004, 1002, 'p004', 'Pipe 6.17', 0)
INSERT INTO item(id, unitMeasure_id, code, name, version) VALUES (1005, 1003, 'l001', 'Liquid C10', 0)
INSERT INTO item(id, unitMeasure_id, code, name, version) VALUES (1006, 1003, 'l002', 'Liquid A008', 0)

INSERT INTO item_tag(item_id, tags_id) VALUES(1006, 1001)
INSERT INTO item_tag(item_id, tags_id) VALUES(1006, 1003)
