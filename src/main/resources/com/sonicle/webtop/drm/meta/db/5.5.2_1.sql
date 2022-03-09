@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE "drm"."causals"
ADD COLUMN "sign" numeric;

update "drm"."causals" set sign = 1 where description ilike '%STRAORDINARIO%';
update "drm"."causals" set sign = 0 where description ilike '%PASTO%';

update "drm"."causals" set sign = -1 where description not ilike '%STRAORDINARIO%' and  description not ilike '%PASTO%';

ALTER TABLE "drm"."causals"
ALTER COLUMN "sign" SET NOT NULL;

ALTER TABLE "drm"."timetable_report_temp"
ADD COLUMN "total_line_hour" varchar(5);

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "default_causal_medical_visit" varchar(5);