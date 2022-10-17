@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "automatic_overtime" bool DEFAULT false;
