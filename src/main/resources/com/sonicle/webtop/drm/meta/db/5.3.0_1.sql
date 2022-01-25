@DataSource[default@com.sonicle.webtop.drm]

CREATE TABLE "drm"."causals" (
"id" varchar(5) NOT NULL,
"description" varchar(100) NOT NULL,
"external_code" varchar(5) NOT NULL,
PRIMARY KEY ("id")
);

INSERT INTO "core"."settings" VALUES ('com.sonicle.webtop.drm', 'integration.gis', 'true');

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "default_causal_working_hours" varchar(5),
ADD COLUMN "default_causal_overtime" varchar(5),
ADD COLUMN "default_causal_permits" varchar(5),
ADD COLUMN "default_causal_holidays" varchar(5),
ADD COLUMN "default_causal_sickness" varchar(5);

ALTER TABLE "drm"."timetable_report_temp"
ADD COLUMN "other" varchar(5),
ADD COLUMN "causal_id" varchar(5);