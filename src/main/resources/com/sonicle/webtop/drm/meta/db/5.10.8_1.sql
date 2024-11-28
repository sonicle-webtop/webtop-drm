@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE drm.timetable_report_temp ALTER COLUMN user_id TYPE varchar(40) USING user_id::varchar(40);
ALTER TABLE drm.timetable_report_temp ADD ticket varchar NULL;
