@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE "drm"."timetable_settings"
ADD COLUMN "calendar_user_id" varchar(50),
ADD COLUMN "default_event_activity_id" int4;
