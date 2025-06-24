CREATE TABLE employee (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   name VARCHAR(255),
   emp_id VARCHAR(255),
   qualification VARCHAR(255),
   experience INTEGER,
   photo_path VARCHAR(255),
   phone_number VARCHAR(255),
   active BOOLEAN,
   email VARCHAR(255),
   blood_group VARCHAR(255),
   gender VARCHAR(255),
   address VARCHAR(255),
   age INTEGER,
   pin INTEGER,
   CONSTRAINT pk_employee PRIMARY KEY (id)
);
CREATE TABLE roles (
  id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_at BIGINT NOT NULL,
  modified_by VARCHAR(255) NOT NULL,
  modified_at BIGINT NOT NULL,
  name VARCHAR(255),
  CONSTRAINT pk_roles PRIMARY KEY (id)
);
ALTER TABLE roles ADD CONSTRAINT uc_roles_name UNIQUE (name);
CREATE TABLE users (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   username VARCHAR(255),
   password VARCHAR(255),
   refresh_token VARCHAR(255),
   active BOOLEAN,
   role_id UUID,
   emp_id UUID,
   CONSTRAINT pk_users PRIMARY KEY (id)
);
ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (username);
ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_EMP FOREIGN KEY (emp_id) REFERENCES employee (id);
ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);
CREATE TABLE assets (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   warranty_end_date BIGINT NOT NULL,
   serial_no VARCHAR(255),
   model VARCHAR(255),
   brand VARCHAR(255),
   purchase_date BIGINT NOT NULL,
   allocated_to BIGINT NOT NULL,
   type VARCHAR(255),
   activeallocations BOOLEAN,
   active BOOLEAN,
   CONSTRAINT pk_assets PRIMARY KEY (id)
);

CREATE TABLE assetsimage (
  id UUID NOT NULL,
   url VARCHAR(255),
   assets_id UUID,
   CONSTRAINT pk_assetsimage PRIMARY KEY (id)
);
ALTER TABLE assetsimage ADD CONSTRAINT FK_ASSETSIMAGE_ON_ASSETS FOREIGN KEY (assets_id) REFERENCES assets (id);

CREATE TABLE assets_allocations (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   emp_id UUID,
   assets_id UUID,
   allocated_from BIGINT NOT NULL,
   allocated_to BIGINT NOT NULL,
   CONSTRAINT pk_assets_allocations PRIMARY KEY (id)
);
ALTER TABLE assets_allocations ADD CONSTRAINT FK_ASSETS_ALLOCATIONS_ON_ASSETS FOREIGN KEY (assets_id) REFERENCES assets (id);
ALTER TABLE assets_allocations ADD CONSTRAINT FK_ASSETS_ALLOCATIONS_ON_EMP FOREIGN KEY (emp_id) REFERENCES employee (id);

CREATE TABLE leave_management (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   from_date BIGINT NOT NULL,
   to_date BIGINT NOT NULL,
   reason VARCHAR(255),
   status BOOLEAN,
   active BOOLEAN,
   leave_apply_date BIGINT NOT NULL,
   emp_id UUID,
   CONSTRAINT pk_leave_management PRIMARY KEY (id)
);
ALTER TABLE leave_management ADD CONSTRAINT FK_LEAVE_MANAGEMENT_ON_EMP FOREIGN KEY (emp_id) REFERENCES employee (id);
CREATE TABLE holiday_management (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   holiday_date BIGINT NOT NULL,
   reason VARCHAR(255),
   active BOOLEAN,
   CONSTRAINT pk_holiday_management PRIMARY KEY (id)
);
CREATE TABLE projects (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   eta_date BIGINT NOT NULL,
   project_name VARCHAR(255),
   language VARCHAR(255),
   status VARCHAR(255),
   proj_id VARCHAR(255),
   active BOOLEAN,
   employee_id UUID,
   CONSTRAINT pk_projects PRIMARY KEY (id)
);
ALTER TABLE projects ADD CONSTRAINT FK_PROJECTS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);
CREATE TABLE modules (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   module_name VARCHAR(255),
   status VARCHAR(255),
   roles VARCHAR(255),
   note VARCHAR(255),
   eta_date BIGINT NOT NULL,
   active BOOLEAN,
   project_id UUID,
   employee_id UUID,
   CONSTRAINT pk_modules PRIMARY KEY (id)
);
ALTER TABLE modules ADD CONSTRAINT FK_MODULES_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);
ALTER TABLE modules ADD CONSTRAINT FK_MODULES_ON_PROJECT FOREIGN KEY (project_id) REFERENCES projects (id);
INSERT INTO roles VALUES(gen_random_uuid(), 'system', extract(epoch from now()), 'system', extract(epoch from now()), 'USER');
INSERT INTO roles VALUES(gen_random_uuid(), 'system', extract(epoch from now()), 'system', extract(epoch from now()), 'ADMIN');