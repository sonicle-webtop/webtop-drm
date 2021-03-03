@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE "drm"."holiday_date" 
ADD COLUMN "holiday_date_id" int4 NOT NULL;

CREATE SEQUENCE "drm"."seq_holiday_dates"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;