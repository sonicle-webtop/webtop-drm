/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CompaniesPicturesRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.CompaniesPicturesRecord> implements org.jooq.Record5<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, byte[]> {

	private static final long serialVersionUID = -1628654551;

	/**
	 * Setter for <code>drm.companies_pictures.company_id</code>.
	 */
	public void setCompanyId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.companies_pictures.company_id</code>.
	 */
	public java.lang.Integer getCompanyId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>drm.companies_pictures.width</code>.
	 */
	public void setWidth(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.companies_pictures.width</code>.
	 */
	public java.lang.Integer getWidth() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>drm.companies_pictures.height</code>.
	 */
	public void setHeight(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.companies_pictures.height</code>.
	 */
	public java.lang.Integer getHeight() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>drm.companies_pictures.media_type</code>.
	 */
	public void setMediaType(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.companies_pictures.media_type</code>.
	 */
	public java.lang.String getMediaType() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>drm.companies_pictures.bytes</code>.
	 */
	public void setBytes(byte[] value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.companies_pictures.bytes</code>.
	 */
	public byte[] getBytes() {
		return (byte[]) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, byte[]> fieldsRow() {
		return (org.jooq.Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, byte[]> valuesRow() {
		return (org.jooq.Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES.WIDTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES.HEIGHT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES.MEDIA_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<byte[]> field5() {
		return com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES.BYTES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value2() {
		return getWidth();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getHeight();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getMediaType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value5() {
		return getBytes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompaniesPicturesRecord value1(java.lang.Integer value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompaniesPicturesRecord value2(java.lang.Integer value) {
		setWidth(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompaniesPicturesRecord value3(java.lang.Integer value) {
		setHeight(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompaniesPicturesRecord value4(java.lang.String value) {
		setMediaType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompaniesPicturesRecord value5(byte[] value) {
		setBytes(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompaniesPicturesRecord values(java.lang.Integer value1, java.lang.Integer value2, java.lang.Integer value3, java.lang.String value4, byte[] value5) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached CompaniesPicturesRecord
	 */
	public CompaniesPicturesRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES);
	}

	/**
	 * Create a detached, initialised CompaniesPicturesRecord
	 */
	public CompaniesPicturesRecord(java.lang.Integer companyId, java.lang.Integer width, java.lang.Integer height, java.lang.String mediaType, byte[] bytes) {
		super(com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES);

		setValue(0, companyId);
		setValue(1, width);
		setValue(2, height);
		setValue(3, mediaType);
		setValue(4, bytes);
	}
}