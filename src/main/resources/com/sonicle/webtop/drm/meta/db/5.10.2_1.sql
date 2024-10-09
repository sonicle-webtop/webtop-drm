@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE drm.timetable_settings ADD default_stamping_mode varchar(1) DEFAULT 'B' NOT NULL;

ALTER TABLE drm.employee_profiles ADD stamping_mode varchar(1) NULL;
