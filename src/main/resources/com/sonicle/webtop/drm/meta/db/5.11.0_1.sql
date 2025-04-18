@DataSource[default@com.sonicle.webtop.drm]

ALTER TABLE "drm"."jobs" ALTER COLUMN "event_id" TYPE varchar(32) USING "event_id"::varchar(32);
ALTER TABLE "drm"."work_reports" ALTER COLUMN "event_id" TYPE varchar(32) USING "event_id"::varchar(32);
ALTER TABLE "drm"."opportunities" ALTER COLUMN "event_id" TYPE varchar(32) USING "event_id"::varchar(32);
ALTER TABLE "drm"."opportunity_actions" ALTER COLUMN "event_id" TYPE varchar(32) USING "event_id"::varchar(32);
ALTER TABLE "drm"."leave_requests" ALTER COLUMN "event_id" TYPE varchar(32) USING "event_id"::varchar(32);
ALTER TABLE "drm"."tickets" ALTER COLUMN "event_id" TYPE varchar(32) USING "event_id"::varchar(32);

ALTER TABLE "drm"."opportunity_interlocutors" ALTER COLUMN "contact_id" TYPE varchar(32) USING "contact_id"::varchar(32);
ALTER TABLE "drm"."opportunity_action_interlocutors" ALTER COLUMN "contact_id" TYPE varchar(32) USING "contact_id"::varchar(32);
ALTER TABLE "drm"."work_reports" ALTER COLUMN "contact_id" TYPE varchar(32) USING "contact_id"::varchar(32);
