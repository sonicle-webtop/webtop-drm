@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE "drm"."timetable_report_temp"
ADD COLUMN "target_user_id" varchar(36) NOT NULL;
