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
"customer_id" varchar(36),
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
"customer_id" varchar(36),
"customer_stat_id" varchar(36),
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

CREATE TABLE "drm"."expense_note_settings" (
"integrate_tracking" varchar(5),
"integrate_mail" varchar(5),
"integrate_cloud" varchar(5),
"domain_id" varchar(30),
"operator_id" varchar(100),
"integrate_calendar" varchar(5)
);

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

--------------
--26/02/2018--
--------------

ALTER TABLE "drm"."employee_hours" RENAME TO "line_hours";

ALTER TABLE "drm"."line_hours" RENAME "employee_profile_id" TO "hour_profile_id";

CREATE TABLE "drm"."hour_profiles" (
"id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"description" varchar(50) NOT NULL,
PRIMARY KEY ("id")
);

ALTER TABLE "drm"."employee_profiles"
ADD COLUMN "hour_profile_id" int4;

CREATE SEQUENCE "drm"."seq_hour_profiles";

CREATE SEQUENCE "drm"."seq_line_hours";

DROP SEQUENCE "drm"."seq_employee_hours";


--------------
--05/03/2018--
--------------

CREATE TABLE "drm"."leave_requests" (
"leave_request_id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"company_id" int4 NOT NULL,
"user_id" varchar(36) NOT NULL,
"manager_id" varchar(36) NOT NULL,
"type" varchar(1) NOT NULL,
"from_date" date NOT NULL,
"to_date" date NOT NULL,
"from_hour" varchar(5),
"to_hour" varchar(5),
"status" varchar(1) NOT NULL,
"notes" text,
"employee_req_timestamp" timestamptz(6) NOT NULL,
"manager_resp_timestamp" timestamptz(6),
"result" bool,
"employee_canc_req" bool,
"employee_canc_req_timestamp" timestamptz(6),
"manager_canc_resp_timetamp" timestamptz(6),
"canc_reason" text,
"canc_result" bool,
PRIMARY KEY ("leave_request_id")
);

CREATE SEQUENCE "drm"."seq_leave_requests";

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "requests_permits_not_remunered" bool,
ADD COLUMN "requests_permits_medical_visits" bool,
ADD COLUMN "requests_permits_contractuals" bool;

CREATE TABLE "drm"."leave_request_documents" (
"leave_request_document_id" varchar(36) NOT NULL,
"leave_request_id" int4 NOT NULL,
"revision_timestamp" timestamptz(6) NOT NULL,
"revision_sequence" int2 NOT NULL,
"filename" varchar(255) NOT NULL,
"size" int4 NOT NULL,
"media_tpye" varchar(100) NOT NULL
);

ALTER TABLE "drm"."leave_request_documents" ADD PRIMARY KEY ("leave_request_document_id");

CREATE SEQUENCE "drm"."seq_leave_request_documents";

DROP TABLE "drm"."expense_notes", "drm"."expense_note_documents", "drm"."expense_note_document_links", "drm"."expense_note_costs", "drm"."expense_note_cost_types", "drm"."expense_note_rows", "drm"."expense_note_row_details", "drm"."expense_note_row_documents", "drm"."expense_note_row_document_links";

DROP SEQUENCE "drm"."seq_expense_note_documents";
DROP SEQUENCE "drm"."seq_expense_notes";
DROP SEQUENCE "drm"."seq_expense_note_costs";
DROP SEQUENCE "drm"."seq_expense_note_rows";
DROP SEQUENCE "drm"."seq_expense_note_row_details";
DROP SEQUENCE "drm"."seq_expense_note_row_documents";

--------------
--19/03/2018--
--------------

CREATE TABLE "drm"."timetable_report_temp" (
"id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"user_id" varchar(36) NOT NULL,
"company_id" int4 NOT NULL,
"date" timestamp(6),
"working_hours" varchar(5),
"overtime" varchar(5),
"paid_leave" varchar(5),
"unpaid_leave" varchar(5),
"holiday" varchar(5),
"medical_visit" varchar(5),
"contractual" varchar(5),
"causal" varchar(5),
"hour" varchar(5),
"detail" varchar(500),
"note" varchar(500),
PRIMARY KEY ("id")
);

CREATE SEQUENCE "drm"."seq_timetable_report_temp";

--------------
--27/03/2018--
--------------

CREATE TABLE "drm"."timetable_events" (
"timetable_event_id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"company_id" int4 NOT NULL,
"user_id" varchar(36) NOT NULL,
"type" varchar(1) NOT NULL,
"date" date NOT NULL,
"hour" varchar(36) NOT NULL,
PRIMARY KEY ("timetable_event_id")
);

CREATE SEQUENCE "drm"."seq_timetable_event";

ALTER TABLE "drm"."timetable_settings"
ALTER COLUMN "break_anomaly" SET DEFAULT false,
ALTER COLUMN "read_only_events" SET DEFAULT false,
ALTER COLUMN "requests_permits_not_remunered" SET DEFAULT false,
ALTER COLUMN "requests_permits_medical_visits" SET DEFAULT false,
ALTER COLUMN "requests_permits_contractuals" SET DEFAULT false;

--------------
--03/04/2018--
--------------

CREATE TABLE "drm"."timetable_stamp" (
"id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"user_id" varchar(36) NOT NULL,
"type" varchar(1) NOT NULL,
"entrance" timestamp(6),
"exit" timestamp(6)
);

ALTER TABLE "drm"."timetable_stamp" ADD PRIMARY KEY ("id");

CREATE SEQUENCE "drm"."seq_timetable_stamp";

--------------
--09/04/2018--
--------------

ALTER TABLE "drm"."timetable_report_temp"
ALTER COLUMN "causal" TYPE varchar(500);

--------------
--20/06/2018--
--------------

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "company_exit" bool DEFAULT false;

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "manage_stamp" bool DEFAULT false;

--------------
--25/06/2018--
--------------

ALTER TABLE "drm"."timetable_events"
ADD COLUMN "leave_request_id" int4 NOT NULL;

--------------
--25/07/2018--
--------------

CREATE TABLE "drm"."opportunity_fields" (
"domain_id" varchar(20) NOT NULL,
"tab_id" varchar(32) NOT NULL,
"field_id" varchar(32) NOT NULL,
"visible" bool NOT NULL,
"required" bool NOT NULL,
"order" int4 NOT NULL,
"label" varchar(32),
PRIMARY KEY ("domain_id", "tab_id", "field_id")
);

INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main','activity', 't', 't', '9', 'Attività');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'causal', 't', 't', '8', 'Causale');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'customer', 't', 't', '2', 'Cliente');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'visit_report', 'customer_potential', 't', 't', '4', 'Potenzialità del Cliente');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'description', 't', 't', '5', 'Descrizione');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'visit_report', 'discoveries', 't', 't', '3', 'Scoperte');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'executed_with', 't', 't', '1', 'Eseguito con');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'interlocutors', 't', 't', '10', 'Interlocutori');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'notes_signature', 'notes', 't', 't', '1', 'Note');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'objective', 't', 't', '7', 'Obiettivo');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'visit_report', 'objective', 't', 't', '1', 'Obiettivo');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'place', 't', 't', '6', 'Luogo');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'visit_report', 'result', 't', 't', '2', 'Esito');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'sector', 't', 't', '4', 'Settore');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'notes_signature', 'signature', 't', 't', '4', 'Firma');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'notes_signature', 'signed_by', 't', 't', '3', 'Firmato da');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'main', 'statistic_customer', 't', 't', '3', 'Destinazione');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'notes_signature', 'status', 't', 't', '2', 'Status');
INSERT INTO "drm"."opportunity_fields" VALUES ('*', 'notes_signature', 'success', 't', 't', '5', 'Successo');

--------------
--26/07/2018--
--------------

CREATE TABLE "drm"."opportunities" (
"id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"company_id" int4 NOT NULL,
"operator_id" varchar(36) NOT NULL,
"date" date NOT NULL,
"from_hour" varchar(5),
"to_hour" varchar(5),
"executed_with" varchar(36),
"customer_id" varchar(36),
"customer_stat_id" varchar(36),
"sector" varchar(255),
"description" varchar(255),
"place" varchar(36),
"objective" text,
"causal_id" int4,
"activity_id" int4,
"objective_2" text,
"result" text,
"discoveries" text,
"customer_potential" text,
"notes" text,
"status_id" int4,
"signed_by" varchar(36),
"signature" bool,
"success" bool,
PRIMARY KEY ("id")
);

CREATE SEQUENCE "drm"."seq_opportunities";

CREATE TABLE "drm"."opportunity_documents" (
"id" varchar(36) NOT NULL,
"opportunity_id" int4 NOT NULL,
"revision_timestamp" timestamptz(6) NOT NULL,
"revision_sequence" int2 NOT NULL,
"filename" varchar(255) NOT NULL,
"size" int4 NOT NULL,
"media_tpye" varchar(100) NOT NULL,
PRIMARY KEY ("id")
);

CREATE TABLE "drm"."opportunity_interlocutors" (
"id" int4 NOT NULL,
"opportunity_id" int4 NOT NULL,
"contact_id" int4 NOT NULL,
PRIMARY KEY ("id")
);

CREATE SEQUENCE "drm"."seq_opportunity_interlocutors";

CREATE TABLE "drm"."opportunity_actions" (
"id" int4 NOT NULL,
"opportunity_id" int4 NOT NULL,
"operator_id" varchar(36) NOT NULL,
"status_id" int4,
"date" date NOT NULL,
"from_hour" varchar(5),
"to_hour" varchar(5),
"description" varchar(255),
"place" varchar(36),
"subsequent_actions" text,
"activity_id" int4,
PRIMARY KEY ("id")
);

CREATE SEQUENCE "drm"."seq_opportunity_actions";

CREATE TABLE "drm"."opportunity_action_interlocutors" (
"id" int4 NOT NULL,
"opportunity_action_id" int4 NOT NULL,
"contact_id" int4 NOT NULL,
PRIMARY KEY ("id")
);

CREATE SEQUENCE "drm"."seq_opportunity_action_interlocutors";

CREATE TABLE "drm"."opportunity_action_documents" (
"id" varchar(36) NOT NULL,
"opportunity_action_id" int4 NOT NULL,
"revision_timestamp" timestamptz(6) NOT NULL,
"revision_sequence" int2 NOT NULL,
"filename" varchar(255) NOT NULL,
"size" int4 NOT NULL,
"media_tpye" varchar(100) NOT NULL,
PRIMARY KEY ("id")
);

--------------
--28/09/2018--
--------------

ALTER TABLE "drm"."opportunity_fields"
ADD COLUMN "show_on_grid" bool DEFAULT false;

--------------
--05/10/2018--
--------------
--INSERITO NELLA CREATE TABLE, DA IGNORARE QUESTA ALTER TABLE SE IL CAMPO è GIà STATO RINOMINATO--
ALTER TABLE "drm"."opportunities" RENAME "won" TO "success";

--------------
--17/10/2018--
--------------

ALTER TABLE "drm"."work_reports"
ALTER COLUMN "reference_no" TYPE varchar(255);

ALTER TABLE "drm"."leave_request_documents" RENAME "media_tpye" TO "media_type";

ALTER TABLE "drm"."opportunity_action_documents" RENAME "media_tpye" TO "media_type";

ALTER TABLE "drm"."opportunity_documents" RENAME "media_tpye" TO "media_type";

ALTER TABLE "drm"."work_reports_attachments" RENAME "media_tpye" TO "media_type";

ALTER TABLE "drm"."leave_request_documents"
ALTER COLUMN "size" TYPE int4,
ALTER COLUMN "media_type" TYPE varchar(255);

ALTER TABLE "drm"."opportunity_action_documents"
ALTER COLUMN "size" TYPE int4,
ALTER COLUMN "media_type" TYPE varchar(255);

ALTER TABLE "drm"."opportunity_documents"
ALTER COLUMN "size" TYPE int4,
ALTER COLUMN "media_type" TYPE varchar(255);

ALTER TABLE "drm"."work_reports_attachments"
ALTER COLUMN "size" TYPE int4,
ALTER COLUMN "media_type" TYPE varchar(255);

ALTER TABLE "drm"."leave_request_documents"
ALTER COLUMN "size" TYPE int8;

ALTER TABLE "drm"."opportunity_action_documents"
ALTER COLUMN "size" TYPE int8;

ALTER TABLE "drm"."opportunity_documents"
ALTER COLUMN "size" TYPE int8;

ALTER TABLE "drm"."work_reports_attachments"
ALTER COLUMN "size" TYPE int8;

CREATE TABLE "drm"."leave_request_documents_data" (
"leave_request_document_id" varchar(36) NOT NULL,
"bytes" bytea NOT NULL
);

ALTER TABLE "drm"."leave_request_documents_data" ADD PRIMARY KEY ("leave_request_document_id");

ALTER TABLE "drm"."leave_request_documents_data" ADD FOREIGN KEY ("leave_request_document_id") REFERENCES "drm"."leave_request_documents" ("leave_request_document_id") ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE "drm"."opportunity_action_documents_data" (
"opportunity_action_document_id" varchar(36) NOT NULL,
"bytes" bytea NOT NULL
);

ALTER TABLE "drm"."opportunity_action_documents_data" ADD PRIMARY KEY ("opportunity_action_document_id");

ALTER TABLE "drm"."opportunity_action_documents_data" ADD FOREIGN KEY ("opportunity_action_document_id") REFERENCES "drm"."opportunity_action_documents" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE "drm"."opportunity_documents_data" (
"opportunity_document_id" varchar(36) NOT NULL,
"bytes" bytea NOT NULL
);

ALTER TABLE "drm"."opportunity_documents_data" ADD PRIMARY KEY ("opportunity_document_id");

ALTER TABLE "drm"."opportunity_documents_data" ADD FOREIGN KEY ("opportunity_document_id") REFERENCES "drm"."opportunity_documents" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE "drm"."work_reports_attachments_data" (
"work_report_attachment_id" varchar(36) NOT NULL,
"bytes" bytea NOT NULL
);

ALTER TABLE "drm"."work_reports_attachments_data" ADD PRIMARY KEY ("work_report_attachment_id");

ALTER TABLE "drm"."work_reports_attachments_data" ADD FOREIGN KEY ("work_report_attachment_id") REFERENCES "drm"."work_reports_attachments" ("work_report_attachment_id") ON DELETE CASCADE ON UPDATE CASCADE;

--------------
--25/10/2018--
--------------

ALTER TABLE "drm"."work_reports"
ADD COLUMN "number" int4,
ADD COLUMN "year" int4;

update work_reports set 
"number" = SPLIT_PART(work_report_no, '-', 1) :: INTEGER, 
"year" = SPLIT_PART(work_report_no, '-', 2) :: INTEGER;

ALTER TABLE "drm"."work_reports"
DROP COLUMN "work_report_no";

--------------
--15/11/2018--
--------------

ALTER TABLE "drm"."work_reports"
ADD COLUMN "event_id" int4;

ALTER TABLE "drm"."opportunity_actions"
ADD COLUMN "event_id" int4;

ALTER TABLE "drm"."opportunities"
ADD COLUMN "event_id" int4;

ALTER TABLE "drm"."leave_requests"
ADD COLUMN "event_id" int4;

--------------
--20/12/2018--
--------------

CREATE TABLE "drm"."expense_note_settings" (
"expense_note_setting_id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"average_maximum" bool,
PRIMARY KEY ("expense_note_setting_id")
);

CREATE TABLE "drm"."cost_types" (
"id" int4 NOT NULL,
"domain_id" varchar(20) NOT NULL,
"description" varchar(30),
"max_import" numeric(15,2),
"cost_type" varchar(1),
"with_others" bool,
"per_person" bool,
"km" bool,
"advance_payment" bool,
"exchange" bool,
PRIMARY KEY ("id")
);

CREATE SEQUENCE "drm"."seq_expense_note_settings";

CREATE SEQUENCE "drm"."seq_cost_types";

ALTER TABLE "drm"."expense_note_settings"
ADD COLUMN "default_currency" varchar(3);

ALTER TABLE "drm"."expense_note_settings"
ADD COLUMN "km_cost" numeric(15,2);

--------------
--07/02/2019--
--------------

ALTER TABLE "drm"."work_reports" RENAME "day_trasfert" TO "business_trip_days";

ALTER TABLE "drm"."work_reports_rows"
ADD COLUMN "extra" bool;

UPDATE work_reports_rows AS wrr
    SET extra = CASE
                 WHEN row_flag = 'Y' THEN true
                ELSE false
              END

ALTER TABLE "drm"."work_reports_rows"
DROP COLUMN "row_flag";