
ALTER TABLE "drm"."work_reports"
ADD COLUMN "timetable_hours" int2;

DROP VIEW  "drm"."vw_work_reports";

CREATE OR REPLACE VIEW "drm"."vw_work_reports" AS 
SELECT 
wr.work_report_id, wr.number, wr.year, wr.reference_no, wr.operator_id, us.display_name AS operator_description, wr.customer_id, rmd.description AS customer_description, 
wr.customer_stat_id, smd.description AS customer_stat_description, wr.causal_id, ca.description AS causal_description, wr.from_date, wr.to_date, wr.business_trip_id, 
bt.description AS business_trip_description, wr.revision_status, wr.charge_to, wr.free_support, wr.company_id, co.name AS company_description, wr.doc_status_id, 
ds.description AS doc_status_description, wr.description, wr.notes, sum(wrr.duration) AS tot_hours
FROM 
drm.work_reports AS wr 
LEFT JOIN drm.companies AS co ON wr.company_id = co.company_id
LEFT JOIN core.master_data AS rmd ON wr.customer_id = rmd.master_data_id AND co.domain_id = rmd.domain_id
LEFT JOIN core.master_data AS smd ON wr.customer_stat_id = smd.master_data_id AND co.domain_id = rmd.domain_id
LEFT JOIN drm.doc_statuses AS ds ON wr.doc_status_id = ds.doc_status_id
LEFT JOIN core.causals AS ca ON wr.causal_id = ca.causal_id AND co.domain_id = ca.domain_id
LEFT JOIN drm.business_trips AS bt ON wr.business_trip_id = bt.business_trip_id AND co.domain_id = bt.domain_id
LEFT JOIN drm.work_reports_rows AS wrr ON wr.work_report_id = wrr.work_report_id
LEFT JOIN core.users AS us ON wr.operator_id = us.user_id AND co.domain_id = us.domain_id
GROUP BY wr.work_report_id, wr.number, wr.year, wr.reference_no, wr.operator_id, us.display_name, wr.customer_id, rmd.description, wr.customer_stat_id, 
smd.description, wr.causal_id, ca.description, wr.from_date, wr.to_date, wr.business_trip_id, 
bt.description, wr.revision_status, wr.charge_to, wr.free_support, wr.company_id, co.name, wr.doc_status_id, ds.description;

CREATE TABLE "drm"."jobs" (
"job_id" varchar(36) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"company_id" int4 NOT NULL,
"operator_id" varchar(36) NOT NULL,
"customer_id" varchar(36),
"customer_stat_id" varchar(36),
"title" varchar(100),
"description" varchar(2048),
"activity_id" int4,
"event_id" int4,
"start_date" timestamptz(6) NOT NULL,
"end_date" timestamptz(6) NOT NULL,
"timezone" varchar(50) NOT NULL,
"ticket_id" varchar(36),
"number" varchar(12),
CONSTRAINT "jobs_pkey" PRIMARY KEY ("job_id")
);

CREATE SEQUENCE "drm"."seq_jobs";

CREATE TABLE "drm"."job_attachments" (
"job_attachment_id" varchar(36) NOT NULL,
"job_id" varchar(36) NOT NULL,
"revision_timestamp" timestamptz(6) NOT NULL,
"revision_sequence" int2 NOT NULL,
"filename" varchar(255) NOT NULL,
"size" int8 NOT NULL,
"media_type" varchar(255) NOT NULL,
CONSTRAINT "job_attachments_pkey" PRIMARY KEY ("job_attachment_id")
);

CREATE TABLE "drm"."job_attachments_data" (
"job_attachment_id" varchar(36) NOT NULL,
"bytes" bytea NOT NULL,
CONSTRAINT "job_attachments_data_pkey" PRIMARY KEY ("job_attachment_id"),
CONSTRAINT "job_attachments_data_job_attachment_id_fkey" FOREIGN KEY ("job_attachment_id") REFERENCES "drm"."job_attachments" ("job_attachment_id") ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE "drm"."work_reports"
ADD COLUMN "domain_id" varchar(20);

DROP VIEW  "drm"."vw_work_reports";

CREATE OR REPLACE VIEW "drm"."vw_work_reports" AS 
SELECT wr.work_report_id, wr.domain_id, wr.number, wr.year, wr.reference_no, wr.operator_id, us.display_name AS operator_description, wr.customer_id, rmd.description AS customer_description, wr.customer_stat_id, smd.description AS customer_stat_description, wr.causal_id, ca.description AS causal_description, wr.from_date, wr.to_date, wr.business_trip_id, bt.description AS business_trip_description, wr.revision_status, wr.charge_to, wr.free_support, wr.company_id, co.name AS company_description, wr.doc_status_id, ds.description AS doc_status_description, wr.description, wr.notes, sum(wrr.duration) AS tot_hours FROM ((((((((drm.work_reports wr LEFT JOIN drm.companies co ON ((wr.company_id = co.company_id))) LEFT JOIN core.master_data rmd ON ((((wr.customer_id)::text = (rmd.master_data_id)::text) AND ((co.domain_id)::text = (rmd.domain_id)::text)))) LEFT JOIN core.master_data smd ON ((((wr.customer_stat_id)::text = (smd.master_data_id)::text) AND ((co.domain_id)::text = (rmd.domain_id)::text)))) LEFT JOIN drm.doc_statuses ds ON ((wr.doc_status_id = ds.doc_status_id))) LEFT JOIN core.causals ca ON (((wr.causal_id = ca.causal_id) AND ((co.domain_id)::text = (ca.domain_id)::text)))) LEFT JOIN drm.business_trips bt ON (((wr.business_trip_id = bt.business_trip_id) AND ((co.domain_id)::text = (bt.domain_id)::text)))) LEFT JOIN drm.work_reports_rows wrr ON (((wr.work_report_id)::text = (wrr.work_report_id)::text))) LEFT JOIN core.users us ON ((((wr.operator_id)::text = (us.user_id)::text) AND ((co.domain_id)::text = (us.domain_id)::text)))) GROUP BY wr.work_report_id, wr.number, wr.year, wr.reference_no, wr.operator_id, us.display_name, wr.customer_id, rmd.description, wr.customer_stat_id, smd.description, wr.causal_id, ca.description, wr.from_date, wr.to_date, wr.business_trip_id, bt.description, wr.revision_status, wr.charge_to, wr.free_support, wr.company_id, co.name, wr.doc_status_id, ds.description;

CREATE SEQUENCE "drm"."seq_ticket_categories";

CREATE TABLE "drm"."ticket_categories" (
"ticket_category_id" int4 DEFAULT nextval('"drm".seq_ticket_categories'::regclass) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"revision_status" varchar(1) NOT NULL,
"external_id" varchar(100),
"description" varchar(50) NOT NULL,
CONSTRAINT "ticket_categories_pkey" PRIMARY KEY ("ticket_category_id")
);

CREATE INDEX "ticket_categories_ak1" ON "drm"."ticket_categories" USING btree ("ticket_category_id", "domain_id");
CREATE INDEX "ticket_categories_ak2" ON "drm"."ticket_categories" USING btree ("external_id", "domain_id");

CREATE SEQUENCE "drm"."seq_tickets_settings";

CREATE TABLE "drm"."tickets_settings" (
"ticket_setting_id" int4 DEFAULT nextval('"drm".seq_tickets_settings'::regclass) NOT NULL,
"domain_id" varchar(20) NOT NULL,
CONSTRAINT "tickets_settings_pkey" PRIMARY KEY ("ticket_setting_id")
);

CREATE TABLE "drm"."tickets" (
"ticket_id" varchar(36) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"company_id" int4 NOT NULL,
"from_operator_id" varchar(36) NOT NULL,
"to_operator_id" varchar(36) NOT NULL,
"customer_id" varchar(36) NOT NULL,
"customer_stat_id" varchar(36) NOT NULL,
"title" varchar(100),
"description" varchar(2048),
"ticket_category_id" int4,
"event_id" int4,
"date" timestamptz(6) NOT NULL,
"timezone" varchar(50) NOT NULL,
"priority_id" varchar(1),
"status_id" int4,
"release" varchar(50),
"environment" varchar(200),
"suggestion" varchar(2048),
"simulation" varchar(2048),
CONSTRAINT "tickets_pkey" PRIMARY KEY ("ticket_id")
);

CREATE TABLE "drm"."ticket_attachments" (
"ticket_attachment_id" varchar(36) NOT NULL,
"ticket_id" varchar(36) NOT NULL,
"revision_timestamp" timestamptz(6) NOT NULL,
"revision_sequence" int2 NOT NULL,
"filename" varchar(255) NOT NULL,
"size" int8 NOT NULL,
"media_type" varchar(255) NOT NULL,
CONSTRAINT "ticket_attachments_pkey" PRIMARY KEY ("ticket_attachment_id")
);

CREATE TABLE "drm"."ticket_attachments_data" (
"ticket_attachment_id" varchar(36) NOT NULL,
"bytes" bytea NOT NULL,
CONSTRAINT "ticket_attachments_data_pkey" PRIMARY KEY ("ticket_attachment_id"),
CONSTRAINT "ticket_attachments_data_ticket_attachment_id_fkey" FOREIGN KEY ("ticket_attachment_id") REFERENCES "drm"."ticket_attachments" ("ticket_attachment_id") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE SEQUENCE "drm"."seq_tickets";

ALTER TABLE "drm"."tickets"
ADD COLUMN "number" TYPE varchar(12);

CREATE SEQUENCE "drm"."seq_activities";

CREATE TABLE "drm"."activities" (
"activity_id" int4 DEFAULT nextval('"drm".seq_activities'::regclass) NOT NULL,
"domain_id" varchar(20) NOT NULL,
"external_id" varchar(50),
"description" varchar(100) NOT NULL,
"customer" bool DEFAULT false NOT NULL,
"timetable" bool DEFAULT false NOT NULL,
"jobs" bool DEFAULT false NOT NULL,
"opportunities" bool DEFAULT false NOT NULL,
CONSTRAINT "activities_pkey" PRIMARY KEY ("activity_id")
);

CREATE INDEX "activities_ak1" ON "drm"."activities" USING btree ("activity_id", "domain_id");
CREATE INDEX "activities_ak2" ON "drm"."activities" USING btree ("external_id", "domain_id");

CREATE SEQUENCE "drm"."seq_activities_groups";

CREATE TABLE "drm"."activities_groups" (
"association_id" int4 DEFAULT nextval('"drm".seq_activities_groups'::regclass) NOT NULL,
"activity_id" int4 NOT NULL,
"group_id" varchar(36) NOT NULL,
CONSTRAINT "activities_groups_pkey" PRIMARY KEY ("association_id")
);

CREATE UNIQUE INDEX "activities_groups_ak1" ON "drm"."activities_groups" USING btree ("activity_id", "group_id");

CREATE OR REPLACE VIEW "drm"."vw_tickets" AS 
SELECT 
tckt.ticket_id, 
tckt.from_operator_id, 
fop.display_name AS from_operator_description, 
tckt.to_operator_id, 
top.display_name AS to_operator_description, 
tckt.customer_id, 
rmd.description AS customer_description, 
tckt.customer_stat_id, 
smd.description AS customer_stat_description, 
tckt.ticket_category_id, 
tc.description AS ticket_category_description, 
tckt.date, 
tckt.timezone,
tckt.company_id, 
co.name AS company_description, 
tckt.title,
tckt.status_id,
ds.description AS status_description,
tckt.priority_id,
tckt.domain_id,
tckt.number,
ds."type",
tckt.description
FROM drm.tickets tckt 
LEFT JOIN drm.companies co ON tckt.company_id = co.company_id AND tckt.domain_id = co.domain_id
LEFT JOIN core.master_data rmd ON tckt.customer_id = rmd.master_data_id AND tckt.domain_id = rmd.domain_id
LEFT JOIN core.master_data smd ON tckt.customer_stat_id = smd.master_data_id AND tckt.domain_id = rmd.domain_id
LEFT JOIN drm.ticket_categories tc ON tckt.ticket_category_id = tc.ticket_category_id AND tckt.domain_id =tc.domain_id
LEFT JOIN core.users fop ON tckt.from_operator_id = fop.user_id AND tckt.domain_id = fop.domain_id
LEFT JOIN core.users top ON tckt.to_operator_id = top.user_id AND tckt.domain_id = top.domain_id
LEFT JOIN drm.doc_statuses ds ON tckt.status_id = ds.doc_status_id;

ALTER TABLE "drm"."activities"
ADD COLUMN "exportable" bool DEFAULT true NOT NULL;

ALTER TABLE "drm"."jobs"
ADD COLUMN "causal_id" int4;

CREATE OR REPLACE VIEW "drm"."vw_jobs" AS 
SELECT 
jb.job_id, 
jb.operator_id, 
us.display_name AS operator_description, 
jb.customer_id, 
rmd.description AS customer_description, 
jb.customer_stat_id, 
smd.description AS customer_stat_description, 
jb.activity_id, 
ac.description AS activity_description, 
jb.start_date, 
jb.end_date,
jb.timezone,
jb.company_id, 
co.name AS company_description, 
jb.title,
jb.ticket_id,
jb.domain_id,
jb.number,
jb.description,
jb.causal_id,
ca.description AS causal_description
FROM drm.jobs jb 
LEFT JOIN drm.companies co ON jb.company_id = co.company_id AND jb.domain_id = co.domain_id
LEFT JOIN core.master_data rmd ON jb.customer_id = rmd.master_data_id AND jb.domain_id = rmd.domain_id
LEFT JOIN core.master_data smd ON jb.customer_stat_id = smd.master_data_id AND jb.domain_id = rmd.domain_id
LEFT JOIN drm.activities ac ON jb.activity_id = ac.activity_id AND jb.domain_id = ac.domain_id
LEFT JOIN core.users us ON jb.operator_id = us.user_id AND jb.domain_id = us.domain_id
LEFT JOIN core.causals ca ON jb.causal_id = ca.causal_id AND jb.domain_id = ca.domain_id;

ALTER TABLE "drm"."work_reports"
ALTER COLUMN "timetable_hours" TYPE int4;

ALTER TABLE "drm"."timetable_report_temp"
ADD COLUMN "work_report_hours" varchar(5);

ALTER TABLE "drm"."timetable_report_temp"
ADD COLUMN "job_hours" varchar(5);

UPDATE line_hours
SET 
"1_h" = (date_part('minute', to_timestamp("1_u",'HH24:MI'))+date_part('hour', to_timestamp("1_u",'HH24:MI'))*60)-(date_part('minute', to_timestamp("1_e",'HH24:MI'))+date_part('hour', to_timestamp("1_e",'HH24:MI'))*60),
"2_h" = (date_part('minute', to_timestamp("2_u",'HH24:MI'))+date_part('hour', to_timestamp("2_u",'HH24:MI'))*60)-(date_part('minute', to_timestamp("2_e",'HH24:MI'))+date_part('hour', to_timestamp("2_e",'HH24:MI'))*60),
"3_h" = (date_part('minute', to_timestamp("3_u",'HH24:MI'))+date_part('hour', to_timestamp("3_u",'HH24:MI'))*60)-(date_part('minute', to_timestamp("3_e",'HH24:MI'))+date_part('hour', to_timestamp("3_e",'HH24:MI'))*60),
"4_h" = (date_part('minute', to_timestamp("4_u",'HH24:MI'))+date_part('hour', to_timestamp("4_u",'HH24:MI'))*60)-(date_part('minute', to_timestamp("4_e",'HH24:MI'))+date_part('hour', to_timestamp("4_e",'HH24:MI'))*60),
"5_h" = (date_part('minute', to_timestamp("5_u",'HH24:MI'))+date_part('hour', to_timestamp("5_u",'HH24:MI'))*60)-(date_part('minute', to_timestamp("5_e",'HH24:MI'))+date_part('hour', to_timestamp("5_e",'HH24:MI'))*60),
"6_h" = (date_part('minute', to_timestamp("6_u",'HH24:MI'))+date_part('hour', to_timestamp("6_u",'HH24:MI'))*60)-(date_part('minute', to_timestamp("6_e",'HH24:MI'))+date_part('hour', to_timestamp("6_e",'HH24:MI'))*60),
"7_h" = (date_part('minute', to_timestamp("7_u",'HH24:MI'))+date_part('hour', to_timestamp("7_u",'HH24:MI'))*60)-(date_part('minute', to_timestamp("7_e",'HH24:MI'))+date_part('hour', to_timestamp("7_e",'HH24:MI'))*60)

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "requests_sickness" bool,
ADD COLUMN "sickness_automatically_approved" bool;

UPDATE timetable_settings SET sickness_automatically_approved = false, requests_sickness = false;

ALTER TABLE "drm"."timetable_report_temp"
ADD COLUMN "sickness" varchar(5);