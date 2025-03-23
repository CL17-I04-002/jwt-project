-- CREACI�N DE MODULOS
INSERT INTO module (name, base_path) VALUES ('PRODUCT', '/products');
INSERT INTO module (name, base_path) VALUES ('AUTH', '/auth');
INSERT INTO module (name, base_path) VALUES ('PERMISSION', '/permissions');


-- CREACI�N DE OPERACIONES
-- PRODUCT
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS','', 'GET', 0, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PRODUCT','', 'POST', 0, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_PRODUCT','/[0-9]*', 'PUT', 0, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_PRODUCT','/[0-9]*/disabled', 'PUT', 0, 1);

-- AUTH
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('AUTHENTICATE','/authenticate', 'POST', 1, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VALIDATE-TOKEN','/validate-token', 'GET', 1, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_MY_PROFILE','/profile','GET', 0, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('LOGOUT','/logout','POST', 1, 2);

-- CREACI�N DE OPERACIONES DE M�DULO PARA RETO SECCION 11
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PERMISSIONS','','GET', 0, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PERMISSION','/[0-9]*','GET', 0, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PERMISSION','','POST', 0, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_PERMISSION','/[0-9]*','DELETE', 0, 3);

-- CREACI�N DE ROLES
INSERT INTO role (name) VALUES ('ADMINISTRATOR');
INSERT INTO role (name) VALUES ('CUSTOMER');

-- CREACI�N DE PERMISOS
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 1);

INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 3);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 4);
-- INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 5);
-- INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 7);
-- INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 8);

INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 9);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 10);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 11);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 12);

-- CREACI�N DE PERMISOS PARA RETO SECCION 11
-- INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 17);
-- INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 18);
-- INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 19);
-- INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 20);

-- CREACI�N DE USUARIOS
INSERT INTO users (username, name, password, role_id) VALUES ('lmarquez', 'luis m�rquez', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 1);