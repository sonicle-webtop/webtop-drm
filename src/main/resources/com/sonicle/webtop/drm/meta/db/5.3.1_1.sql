@DataSource[default@com.sonicle.webtop.drm]

CREATE INDEX "ttrt_ak1" ON "drm"."timetable_report_temp" USING btree ("domain_id"  , "user_id"  , "date"  );