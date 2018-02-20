@DataSource[default@com.sonicle.webtop.drm]

CREATE SCHEMA "drm";

-- ----------------------------
-- Sequence structure for "drm"."seq_business_trips"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_business_trips";

-- ----------------------------
-- Sequence structure for "drm"."seq_companies"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_companies";

-- ----------------------------
-- Sequence structure for "drm"."seq_companies_users"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_companies_users";

-- ----------------------------
-- Sequence structure for "drm"."seq_doc_statuses"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_doc_statuses";

-- ----------------------------
-- Sequence structure for "drm"."seq_doc_statuses_groups"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_doc_statuses_groups";

-- ----------------------------
-- Sequence structure for "drm"."seq_folders_groups"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_folders_groups";

-- ----------------------------
-- Sequence structure for "drm"."seq_groups_users"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_groups_users";

-- ----------------------------
-- Sequence structure for "drm"."seq_profiles_masterdata"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_profiles_masterdata";

-- ----------------------------
-- Sequence structure for "drm"."seq_profiles_supervised_users"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_profiles_supervised_users";

-- ----------------------------
-- Sequence structure for "drm"."seq_seq_profiles_members"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_profiles_members";

-- ----------------------------
-- Sequence structure for "drm"."seq_work_report_details"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_work_report_details";

-- ----------------------------
-- Sequence structure for "drm"."seq_work_reports"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_work_reports";

-- ----------------------------
-- Sequence structure for "drm"."seq_work_reports_count"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_work_reports_count";

-- ----------------------------
-- Sequence structure for "drm"."seq_work_reports_settings"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_work_reports_settings";

-- ----------------------------
-- Sequence structure for "drm"."seq_work_types"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_work_types";

-- ----------------------------
-- Table structure for "drm"."business_trips"
-- ----------------------------
CREATE TABLE "drm"."business_trips" (
"business_trip_id" int4 DEFAULT nextval('"drm".seq_business_trips'::regclass) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"revision_status" varchar(1) NOT NULL,
"external_id" varchar(100),
"description" varchar(50) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."companies"
-- ----------------------------
CREATE TABLE "drm"."companies" (
"company_id" int4 DEFAULT nextval('"drm".seq_companies'::regclass) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"name" varchar(100) NOT NULL,
"address" varchar(100),
"postal_code" varchar(10),
"city" varchar(100),
"state" varchar(100),
"phone" varchar(30),
"fax" varchar(30),
"vat" varchar(50),
"tax_code" varchar(16),
"rea" varchar(20),
"business_register" varchar(20),
"revision_status" char(1),
"footer_columns" varchar(10),
"footer_column_left" varchar(100),
"footer_column_right" varchar(100),
"logo_upload_id" varchar(36)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."companies_users"
-- ----------------------------
CREATE TABLE "drm"."companies_users" (
"association_id" int4 DEFAULT nextval('"drm".seq_companies_users'::regclass) NOT NULL,
"company_id" int4 NOT NULL,
"user_id" varchar(36) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."doc_statuses"
-- ----------------------------
CREATE TABLE "drm"."doc_statuses" (
"doc_status_id" int4 DEFAULT nextval('"drm".seq_doc_statuses'::regclass) NOT NULL,
"name" varchar(100),
"description" varchar(255),
"type" varchar(1) NOT NULL,
"built_in" bool DEFAULT false NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."doc_statuses_groups"
-- ----------------------------
CREATE TABLE "drm"."doc_statuses_groups" (
"association_id" int4 DEFAULT nextval('"drm".seq_doc_statuses_groups'::regclass) NOT NULL,
"doc_status_id" int4 NOT NULL,
"group_id" varchar(36) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."enabled_programs"
-- ----------------------------
CREATE TABLE "drm"."enabled_programs" (
"domain_id" varchar(20) NOT NULL,
"group_id" varchar(38) NOT NULL,
"program_id" varchar(50) NOT NULL,
"parent_id" varchar(50) NOT NULL,
"read_only" bool NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."folders"
-- ----------------------------
CREATE TABLE "drm"."folders" (
"folder_id" varchar(36) NOT NULL,
"name" varchar(100) NOT NULL,
"description" varchar(255),
"expired" bool DEFAULT false NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."folders_groups"
-- ----------------------------
CREATE TABLE "drm"."folders_groups" (
"association_id" int4 DEFAULT nextval('"drm".seq_folders_groups'::regclass) NOT NULL,
"folder_id" varchar(36) NOT NULL,
"group_id" varchar(36) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."groups"
-- ----------------------------
CREATE TABLE "drm"."groups" (
"group_id" varchar(36) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"group_category" varchar(1) NOT NULL,
"name" varchar(100) NOT NULL,
"description" varchar(255),
"group_type" varchar(1) NOT NULL,
"customer_id" varchar(15),
"supervisior_user_id" varchar(100)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."groups_users"
-- ----------------------------
CREATE TABLE "drm"."groups_users" (
"association_id" int4 DEFAULT nextval('"drm".seq_groups_users'::regclass) NOT NULL,
"group_id" varchar(36) NOT NULL,
"user_id" varchar(36) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."profiles"
-- ----------------------------
CREATE TABLE "drm"."profiles" (
"profile_id" varchar(36) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"description" varchar(255) NOT NULL,
"type" varchar(1) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."profiles_masterdata"
-- ----------------------------
CREATE TABLE "drm"."profiles_masterdata" (
"id" int4 DEFAULT nextval('"drm".seq_profiles_masterdata'::regclass) NOT NULL,
"profile_id" varchar(36) NOT NULL,
"master_data_id" varchar(36) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."profiles_supervised_users"
-- ----------------------------
CREATE TABLE "drm"."profiles_supervised_users" (
"id" int4 DEFAULT nextval('"drm".seq_profiles_supervised_users'::regclass) NOT NULL,
"profile_id" varchar(36) NOT NULL,
"user_id" varchar(36) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."profiles_members"
-- ----------------------------
CREATE TABLE "drm"."profiles_members" (
"id" int4 DEFAULT nextval('"drm".seq_profiles_members'::regclass) NOT NULL,
"profile_id" varchar(36) NOT NULL,
"user_id" varchar(36) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."work_reports"
-- ----------------------------
CREATE TABLE "drm"."work_reports" (
"work_report_id" varchar(36) NOT NULL,
"work_report_no" varchar(36),
"company_id" int4,
"user_id" varchar(36),
"revision_status" varchar(1),
"revision_timestamp" timestamptz(6),
"revision_sequence" int4,
"doc_status_id" int4,
"contact_id" int4,
"customer_id" varchar(15),
"customer_stat_id" varchar(15),
"from_date" timestamptz(6),
"to_date" timestamptz(6),
"reference_no" varchar(30),
"causal" varchar(100),
"causal_id" int4,
"ddt_no" varchar(15),
"ddt_date" timestamptz(6),
"notes" varchar(1024),
"description" varchar(2048),
"apply_signature" bool,
"charge_to" bool,
"free_support" bool,
"business_trip_id" int4,
"day_trasfert" int2
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."work_reports_attachments"
-- ----------------------------
CREATE TABLE "drm"."work_reports_attachments" (
"work_report_attachment_id" varchar(36) NOT NULL,
"work_report_id" varchar(36) NOT NULL,
"revision_timestamp" timestamptz(6) NOT NULL,
"revision_sequence" int2 NOT NULL,
"filename" varchar(255) NOT NULL,
"size" int4 NOT NULL,
"media_tpye" varchar(100) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."work_reports_rows"
-- ----------------------------
CREATE TABLE "drm"."work_reports_rows" (
"id" int4 DEFAULT nextval('"drm".seq_work_report_details'::regclass) NOT NULL,
"work_report_id" varchar(36),
"row_no" int2,
"work_type_id" int4,
"duration" int2,
"row_flag" varchar(1)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."work_reports_settings"
-- ----------------------------
CREATE TABLE "drm"."work_reports_settings" (
"work_report_setting_id" int4 DEFAULT nextval('"drm".seq_work_reports_settings'::regclass) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"warranty_text" varchar(1024)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "drm"."work_types"
-- ----------------------------
CREATE TABLE "drm"."work_types" (
"work_type_id" int4 DEFAULT nextval('"drm".seq_work_types'::regclass) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"revision_status" varchar(1) NOT NULL,
"external_id" varchar(100),
"description" varchar(50) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Primary Key structure for table "drm"."business_trips"
-- ----------------------------
ALTER TABLE "drm"."business_trips" ADD PRIMARY KEY ("business_trip_id");

-- ----------------------------
-- Primary Key structure for table "drm"."companies"
-- ----------------------------
ALTER TABLE "drm"."companies" ADD PRIMARY KEY ("company_id");

-- ----------------------------
-- Indexes structure for table companies_users
-- ----------------------------
CREATE UNIQUE INDEX "companies_users_ak1" ON "drm"."companies_users" USING btree ("company_id", "user_id");

-- ----------------------------
-- Primary Key structure for table "drm"."companies_users"
-- ----------------------------
ALTER TABLE "drm"."companies_users" ADD PRIMARY KEY ("association_id");

-- ----------------------------
-- Primary Key structure for table "drm"."doc_statuses"
-- ----------------------------
ALTER TABLE "drm"."doc_statuses" ADD PRIMARY KEY ("doc_status_id");

-- ----------------------------
-- Indexes structure for table doc_statuses_groups
-- ----------------------------
CREATE UNIQUE INDEX "doc_statuses_groups_ak1" ON "drm"."doc_statuses_groups" USING btree ("doc_status_id", "group_id");

-- ----------------------------
-- Primary Key structure for table "drm"."doc_statuses_groups"
-- ----------------------------
ALTER TABLE "drm"."doc_statuses_groups" ADD PRIMARY KEY ("association_id");

-- ----------------------------
-- Primary Key structure for table "drm"."enabled_programs"
-- ----------------------------
ALTER TABLE "drm"."enabled_programs" ADD PRIMARY KEY ("domain_id", "group_id");

-- ----------------------------
-- Primary Key structure for table "drm"."folders"
-- ----------------------------
ALTER TABLE "drm"."folders" ADD PRIMARY KEY ("folder_id");

-- ----------------------------
-- Primary Key structure for table "drm"."folders_groups"
-- ----------------------------
ALTER TABLE "drm"."folders_groups" ADD PRIMARY KEY ("association_id");

-- ----------------------------
-- Indexes structure for table groups
-- ----------------------------
CREATE INDEX "groups_ak1" ON "drm"."groups" USING btree ("domain_id", "group_category");

-- ----------------------------
-- Primary Key structure for table "drm"."groups"
-- ----------------------------
ALTER TABLE "drm"."groups" ADD PRIMARY KEY ("group_id");

-- ----------------------------
-- Primary Key structure for table "drm"."groups_users"
-- ----------------------------
ALTER TABLE "drm"."groups_users" ADD PRIMARY KEY ("association_id");

-- ----------------------------
-- Primary Key structure for table "drm"."profiles"
-- ----------------------------
ALTER TABLE "drm"."profiles" ADD PRIMARY KEY ("profile_id");

-- ----------------------------
-- Primary Key structure for table "drm"."profiles_masterdata"
-- ----------------------------
ALTER TABLE "drm"."profiles_masterdata" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "drm"."profiles_supervised_users"
-- ----------------------------
ALTER TABLE "drm"."profiles_supervised_users" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "drm"."profiles_members"
-- ----------------------------
ALTER TABLE "drm"."profiles_members" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "drm"."work_reports"
-- ----------------------------
ALTER TABLE "drm"."work_reports" ADD PRIMARY KEY ("work_report_id");

-- ----------------------------
-- Primary Key structure for table "drm"."work_reports_attachments"
-- ----------------------------
ALTER TABLE "drm"."work_reports_attachments" ADD PRIMARY KEY ("work_report_attachment_id");

-- ----------------------------
-- Primary Key structure for table "drm"."work_reports_rows"
-- ----------------------------
ALTER TABLE "drm"."work_reports_rows" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "drm"."work_reports_settings"
-- ----------------------------
ALTER TABLE "drm"."work_reports_settings" ADD PRIMARY KEY ("work_report_setting_id");

-- ----------------------------
-- Indexes structure for table work_types
-- ----------------------------
CREATE INDEX "work_types_ak1" ON "drm"."work_types" USING btree ("work_type_id", "domain_id");

-- ----------------------------
-- Primary Key structure for table "drm"."work_types"
-- ----------------------------
ALTER TABLE "drm"."work_types" ADD PRIMARY KEY ("work_type_id");

-- ----------------------------
-- Align service version
-- ----------------------------
@DataSource[default@com.sonicle.webtop.core]
DELETE FROM "core"."settings" WHERE ("settings"."service_id" = 'com.sonicle.webtop.drm') AND ("settings"."key" = 'manifest.version');
INSERT INTO "core"."settings" ("service_id", "key", "value") VALUES ('com.sonicle.webtop.drm', 'manifest.version', '5.0.0');

-- ----------------------------
-- 23/10/2017
-- ----------------------------

ALTER TABLE "drm"."work_reports"
ALTER COLUMN "company_id" SET NOT NULL,
ALTER COLUMN "user_id" SET NOT NULL,
ALTER COLUMN "revision_status" SET NOT NULL,
ALTER COLUMN "revision_timestamp" SET NOT NULL,
ALTER COLUMN "doc_status_id" SET NOT NULL,
ALTER COLUMN "customer_id" SET NOT NULL,
ALTER COLUMN "customer_stat_id" SET NOT NULL,
ALTER COLUMN "from_date" SET NOT NULL,
ALTER COLUMN "to_date" SET NOT NULL;

ALTER TABLE "drm"."work_reports" RENAME "user_id" TO "operator_id";

ALTER TABLE "drm"."work_reports"
DROP COLUMN "causal",
ALTER COLUMN "causal_id" SET NOT NULL;

ALTER TABLE "drm"."work_reports"
ALTER COLUMN "from_date" TYPE date,
ALTER COLUMN "to_date" TYPE date,
ALTER COLUMN "ddt_date" TYPE date;

ALTER TABLE "drm"."companies"
ALTER COLUMN "footer_column_left" TYPE text,
ALTER COLUMN "footer_column_right" TYPE text;

ALTER TABLE "drm"."work_reports_settings"
ALTER COLUMN "warranty_text" TYPE text;

CREATE TABLE "drm"."companies_pictures" (
"company_id" int4 NOT NULL,
"width" int4,
"height" int4,
"media_type" varchar(50),
"bytes" bytea
);


ALTER TABLE "drm"."companies_pictures" ADD PRIMARY KEY ("company_id");

ALTER TABLE "drm"."companies"
DROP COLUMN "logo_upload_id";

-- ----------------------------
-- 14/12/2017
-- ----------------------------

CREATE TABLE "drm"."expense_notes" (
"expense_note_id" numeric(38) NOT NULL,
"currency" varchar(10) NOT NULL,
"amountkm" numeric NOT NULL,
"subtotal" numeric,
"advance" numeric,
"total" numeric,
"operator_id" varchar(100),
"status" varchar(38),
"domain_id" varchar(20),
"sign" varchar(5),
"from_date" timestamp(6),
"to_date" timestamp(6),
"company_id" numeric(38) DEFAULT 1,
"description" varchar(150),
"statusd" varchar(3)
);

CREATE TABLE "drm"."expense_note_documents" (
"expense_note_document_id" numeric(38) NOT NULL,
"file" oid,
"filename" varchar(1024),
"expense_note_id" numeric(38),
"revision" timestamp(6) NOT NULL,
"operator_id_lock_file" varchar(100)
);

CREATE TABLE "drm"."expense_note_document_links" (
"expense_note_id" numeric(38) NOT NULL,
"expense_note_document_id" numeric(38) NOT NULL,
"operator_id" varchar(100),
"domain_id" varchar(20)
);

CREATE TABLE "drm"."expense_note_costs" (
"type_id" numeric(38) NOT NULL,
"description" varchar(30),
"max_import" varchar(15),
"type_cost" numeric(38),
"type_cost_present" varchar(5),
"type_cost_km" varchar(5),
"domain_id" varchar(30),
"consumption" varchar(5),
"type_cost_advance" varchar(5) DEFAULT 'false'::character varying,
"type_cost_unlock_change" varchar(5) DEFAULT 'false'::character varying,
"type_cost_person" varchar(5) DEFAULT 'false'::character varying
);

CREATE TABLE "drm"."expense_note_cost_types" (
"cost_type_id" numeric(38) NOT NULL,
"description" varchar(30),
"domain_id" varchar(30)
);

CREATE TABLE "drm"."expense_note_rows" (
"expense_note_row_id" numeric(38) NOT NULL,
"expense_note_id" numeric(38),
"revision" timestamp(6),
"description" varchar(150),
"km" numeric,
"stay" numeric,
"highway" numeric,
"park" numeric,
"meals" numeric,
"amount_km" numeric,
"other" numeric,
"total" numeric,
"date" timestamp(6),
"operator_id" varchar(100),
"domain_id" varchar(20),
"customer_id" varchar(15),
"payment_company" varchar(5),
"invoice" varchar(5),
"invoice_number" varchar(20),
"point_of_sale" varchar(15)
);

CREATE TABLE "drm"."expense_note_row_details" (
"expense_note_row_detail_id" numeric(38) NOT NULL,
"expense_note_id" numeric(38),
"revision" timestamp(6),
"type_id" numeric(38),
"total" numeric,
"date" timestamp(6),
"operator_id" varchar(100),
"domain_id" varchar(20),
"payment_company" varchar(5),
"invoice" varchar(5),
"invoice_number" varchar(20),
"present" varchar(300),
"customer_id" varchar(15),
"point_of_sale" varchar(15),
"km" numeric,
"total_doc" numeric,
"consumptive" varchar(5),
"currency" varchar(10),
"change" varchar(10),
"currency_cost_doc" varchar(10) DEFAULT 'EUR'::character varying,
"description" varchar(150)
);

CREATE TABLE "drm"."expense_note_row_documents" (
"expense_note_row_document_id" numeric(38) NOT NULL,
"expense_note_row_detail_id" numeric(38) NOT NULL,
"revision" timestamp(6) NOT NULL,
"file" oid,
"filename" varchar(60),
"expense_note_id" numeric(38),
"operator_id" varchar(100),
"domain_id" varchar(20),
"operator_id_lock_file" varchar(100)
);

CREATE TABLE "drm"."expense_note_row_document_links" (
"expense_note_row_detail_id" numeric(38) NOT NULL,
"expense_note_id" numeric(38),
"expense_note_row_document_id" numeric(38) NOT NULL,
"operator_id" varchar(100),
"domain_id" varchar(20)
);

CREATE TABLE "drm"."expense_note_settings" (
"integrate_tracking" varchar(5),
"integrate_mail" varchar(5),
"integrate_cloud" varchar(5),
"domain_id" varchar(30),
"operator_id" varchar(100),
"integrate_calendar" varchar(5)
);


ALTER TABLE "drm"."expense_notes" ADD PRIMARY KEY ("expense_note_id");
ALTER TABLE "drm"."expense_note_documents" ADD PRIMARY KEY ("expense_note_document_id");
ALTER TABLE "drm"."expense_note_costs" ADD PRIMARY KEY ("type_id");
ALTER TABLE "drm"."expense_note_cost_types" ADD PRIMARY KEY ("cost_type_id");
ALTER TABLE "drm"."expense_note_rows" ADD PRIMARY KEY ("expense_note_row_id");
ALTER TABLE "drm"."expense_note_row_details" ADD PRIMARY KEY ("expense_note_row_detail_id");
ALTER TABLE "drm"."expense_note_row_documents" ADD PRIMARY KEY ("expense_note_row_document_id");

CREATE SEQUENCE "drm"."seq_expense_note_documents";
CREATE SEQUENCE "drm"."seq_expense_notes";
CREATE SEQUENCE "drm"."seq_expense_note_costs";
CREATE SEQUENCE "drm"."seq_expense_note_rows";
CREATE SEQUENCE "drm"."seq_expense_note_row_details";
CREATE SEQUENCE "drm"."seq_expense_note_row_documents";


CREATE TABLE "drm"."timetable_attendancesheet" (
"operator_id" varchar(15) DEFAULT 0 NOT NULL,
"group_id" int2 DEFAULT 0 NOT NULL,
"day" varchar(10) NOT NULL,
"h_work" numeric(15,2) DEFAULT 0 NOT NULL,
"h_permits" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_1" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_2" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_3" numeric(15,2) DEFAULT 0 NOT NULL,
"h_holidays" numeric(15,2) DEFAULT 0 NOT NULL,
"h_disease" numeric(15,2) DEFAULT 0 NOT NULL,
"h_total" numeric(15,2) DEFAULT 0 NOT NULL,
"no_load" int2 DEFAULT 0 NOT NULL,
"h_reports" numeric(15,2) DEFAULT 0 NOT NULL,
"h_work_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_work_h" numeric(15,2) DEFAULT 0 NOT NULL,
"h_work_min" numeric(15,2) DEFAULT 0 NOT NULL,
"h_permits_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_permits_h" numeric(15,2) DEFAULT 0 NOT NULL,
"h_permits_min" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_1_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_2_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_2_h" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_2_min" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_3_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_3_h" numeric(15,2) DEFAULT 0 NOT NULL,
"h_extra_3_min" numeric(15,2) DEFAULT 0 NOT NULL,
"h_holidays_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_holidays_h" numeric(15,2) DEFAULT 0 NOT NULL,
"h_holidays_min" numeric(15,2) DEFAULT 0 NOT NULL,
"h_disease_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_disease_h" numeric(15,2) DEFAULT 0 NOT NULL,
"h_disease_min" numeric(15,2) DEFAULT 0 NOT NULL,
"h_total_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_total_h" numeric(15,2) DEFAULT 0 NOT NULL,
"h_total_min" numeric(15,2) DEFAULT 0 NOT NULL,
"h_reports_tot" numeric(15,2) DEFAULT 0 NOT NULL,
"h_reports_h" numeric(15,2) DEFAULT 0 NOT NULL,
"h_reports_min" numeric(15,2) DEFAULT 0 NOT NULL,
"username" varchar(50) NOT NULL
);

CREATE TABLE "drm"."timetable_movements" (
"movement_id" int4 DEFAULT nextval('timetable_movements_movement_id_seq'::regclass) NOT NULL,
"type_movement" varchar(2) NOT NULL,
"operator_id" varchar(15) NOT NULL,
"date_from" timestamp(6) NOT NULL,
"date_to" timestamp(6) NOT NULL,
"duration" time(6) NOT NULL,
"description" varchar(1024),
"reference" varchar(100),
"state" varchar(30),
"category" varchar(30),
"n_report" varchar(100),
"username" varchar(50) NOT NULL
);

CREATE TABLE "drm"."timetable_requests_holiday" (
"holiday_id" numeric(38) NOT NULL,
"login" varchar(100),
"domain_id" varchar(20),
"type" varchar(5),
"responsible" varchar(100),
"email_responsible" varchar(80),
"date_from" timestamp(6),
"date_to" timestamp(6),
"h_from" varchar(5),
"h_to" varchar(5),
"status" varchar(5),
"approved" varchar(5),
"date_request" timestamp(6),
"request_cancel" varchar(5),
"event_id" numeric(38),
"note" varchar(1500)
);

ALTER TABLE "drm"."timetable_attendancesheet" ADD PRIMARY KEY ("operator_id", "day");

ALTER TABLE "drm"."timetable_movements" ADD PRIMARY KEY ("movement_id");

ALTER TABLE "drm"."timetable_requests_holiday" ADD PRIMARY KEY ("holiday_id");

CREATE SEQUENCE "drm"."seq_timetable_movements";
CREATE SEQUENCE "drm"."seq_timetable_requests_holiday";

CREATE TABLE "drm"."line_managers" (
"domain_id" varchar(20) NOT NULL,
"user_id" varchar(36) NOT NULL,
PRIMARY KEY ("domain_id", "user_id")
);

CREATE TABLE "drm"."line_manager_users" (
"domain_id" varchar(20) NOT NULL,
"line_manager_user_id" varchar(36) NOT NULL,
"user_id" varchar(36) NOT NULL,
PRIMARY KEY ("domain_id", "line_manager_user_id", "user_id")
);

CREATE TABLE "drm"."holiday_date" (
"domain_id" varchar(30),
"date" date,
"description" varchar(50) NOT NULL,
PRIMARY KEY ("domain_id", "date")
);

CREATE TABLE "drm"."timetable_settings" (
"timetable_setting_id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"allowed_addresses" varchar(50),
"allowed_users" varchar(50),
"staff_office_email" varchar(50),
"requests_holidays_permits_previous_dates" bool,
"total_tolerance_in_minutes" varchar(50),
"rounding" varchar(50),
"minimum_extraordinary" varchar(50),
"break_anomaly" bool,
"read_only_events" bool,
PRIMARY KEY ("timetable_setting_id")
);

CREATE SEQUENCE "drm"."seq_timetable_settings";

CREATE TABLE "drm"."employee_profiles" (
"id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"user_id" varchar(36) NOT NULL,
"number" varchar(50),
"tolerance" varchar(50),
"extraordinary" bool,
"only_presence" bool,
PRIMARY KEY ("id")
);

CREATE SEQUENCE "drm"."seq_employee_profiles";

CREATE TABLE "drm"."employee_hours" (
"id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"employee_profile_id" int4 NOT NULL,
"line_id" int4 NOT NULL,
"1_e" varchar(5),
"1_u" varchar(5),
"1_h" varchar(5),
"2_e" varchar(5),
"2_u" varchar(5),
"2_h" varchar(5),
"3_e" varchar(5),
"3_u" varchar(5),
"3_h" varchar(5),
"4_e" varchar(5),
"4_u" varchar(5),
"4_h" varchar(5),
"5_e" varchar(5),
"5_u" varchar(5),
"5_h" varchar(5),
"6_e" varchar(5),
"6_u" varchar(5),
"6_h" varchar(5),
"7_e" varchar(5),
"7_u" varchar(5),
"7_h" varchar(5)
);

CREATE SEQUENCE "drm"."seq_employee_hours";