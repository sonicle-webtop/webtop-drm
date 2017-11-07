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
-- Sequence structure for "drm"."seq_PROFILES_MEMBERS"
-- ----------------------------
CREATE SEQUENCE "drm"."seq_PROFILES_MEMBERS";

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
-- Table structure for "drm"."PROFILES_MEMBERS"
-- ----------------------------
CREATE TABLE "drm"."PROFILES_MEMBERS" (
"id" int4 DEFAULT nextval('"drm".seq_PROFILES_MEMBERS'::regclass) NOT NULL,
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
-- Primary Key structure for table "drm"."PROFILES_MEMBERS"
-- ----------------------------
ALTER TABLE "drm"."PROFILES_MEMBERS" ADD PRIMARY KEY ("id");

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
