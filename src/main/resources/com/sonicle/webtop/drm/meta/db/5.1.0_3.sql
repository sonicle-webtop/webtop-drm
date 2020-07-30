@DataSource[default@com.sonicle.webtop.drm]


-- Eseguire una Truncate a mano delle tabelle: 
-- opportunities 
-- opportunity_actions
-- opportunity_action_documents
-- opportunity_documents
-- opportunity_documents_data
-- opportunity_action_documents_data
-- opportunity_interlocutors
-- opportunity_action_interlocutors

ALTER TABLE "drm"."opportunity_actions"
DROP COLUMN "date",
DROP COLUMN "from_hour",
DROP COLUMN "to_hour";

ALTER TABLE "drm"."opportunities"
DROP COLUMN "date",
DROP COLUMN "from_hour",
DROP COLUMN "to_hour";

ALTER TABLE "drm"."opportunities"
ADD COLUMN "start_date" timestamp(6) NOT NULL,
ADD COLUMN "end_date" timestamp(6) NOT NULL;

ALTER TABLE "drm"."opportunity_actions"
ADD COLUMN "start_date" timestamp(6) NOT NULL,
ADD COLUMN "end_date" timestamp(6) NOT NULL;