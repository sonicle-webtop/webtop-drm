@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE drm.timetable_settings ALTER COLUMN default_stamping_mode TYPE varchar(5) USING default_stamping_mode::varchar(5);
update drm.timetable_settings ts set default_stamping_mode = 'OS' where default_stamping_mode = 'B';

ALTER TABLE drm.employee_profiles ALTER COLUMN stamping_mode TYPE varchar(5) USING stamping_mode::varchar(5);
update drm.employee_profiles ep set stamping_mode = 'OS' where stamping_mode = 'B';