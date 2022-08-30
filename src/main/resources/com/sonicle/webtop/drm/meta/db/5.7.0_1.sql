@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "minimum_number_of_hours_per_ticket" int4;

ALTER TABLE "drm"."employee_profiles"
ADD COLUMN "minimum_number_of_hours_per_ticket" int4;

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "ticket_management" bool;