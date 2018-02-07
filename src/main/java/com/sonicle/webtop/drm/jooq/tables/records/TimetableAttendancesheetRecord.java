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
public class TimetableAttendancesheetRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.TimetableAttendancesheetRecord> {

	private static final long serialVersionUID = 1903584322;

	/**
	 * Setter for <code>drm.timetable_attendancesheet.operator_id</code>.
	 */
	public void setOperatorId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.operator_id</code>.
	 */
	public java.lang.String getOperatorId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.group_id</code>.
	 */
	public void setGroupId(java.lang.Short value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.group_id</code>.
	 */
	public java.lang.Short getGroupId() {
		return (java.lang.Short) getValue(1);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.day</code>.
	 */
	public void setDay(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.day</code>.
	 */
	public java.lang.String getDay() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_work</code>.
	 */
	public void setHWork(java.math.BigDecimal value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_work</code>.
	 */
	public java.math.BigDecimal getHWork() {
		return (java.math.BigDecimal) getValue(3);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_permits</code>.
	 */
	public void setHPermits(java.math.BigDecimal value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_permits</code>.
	 */
	public java.math.BigDecimal getHPermits() {
		return (java.math.BigDecimal) getValue(4);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_1</code>.
	 */
	public void setHExtra_1(java.math.BigDecimal value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_1</code>.
	 */
	public java.math.BigDecimal getHExtra_1() {
		return (java.math.BigDecimal) getValue(5);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_2</code>.
	 */
	public void setHExtra_2(java.math.BigDecimal value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_2</code>.
	 */
	public java.math.BigDecimal getHExtra_2() {
		return (java.math.BigDecimal) getValue(6);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_3</code>.
	 */
	public void setHExtra_3(java.math.BigDecimal value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_3</code>.
	 */
	public java.math.BigDecimal getHExtra_3() {
		return (java.math.BigDecimal) getValue(7);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_holidays</code>.
	 */
	public void setHHolidays(java.math.BigDecimal value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_holidays</code>.
	 */
	public java.math.BigDecimal getHHolidays() {
		return (java.math.BigDecimal) getValue(8);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_disease</code>.
	 */
	public void setHDisease(java.math.BigDecimal value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_disease</code>.
	 */
	public java.math.BigDecimal getHDisease() {
		return (java.math.BigDecimal) getValue(9);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_total</code>.
	 */
	public void setHTotal(java.math.BigDecimal value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_total</code>.
	 */
	public java.math.BigDecimal getHTotal() {
		return (java.math.BigDecimal) getValue(10);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.no_load</code>.
	 */
	public void setNoLoad(java.lang.Short value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.no_load</code>.
	 */
	public java.lang.Short getNoLoad() {
		return (java.lang.Short) getValue(11);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_reports</code>.
	 */
	public void setHReports(java.math.BigDecimal value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_reports</code>.
	 */
	public java.math.BigDecimal getHReports() {
		return (java.math.BigDecimal) getValue(12);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_work_tot</code>.
	 */
	public void setHWorkTot(java.math.BigDecimal value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_work_tot</code>.
	 */
	public java.math.BigDecimal getHWorkTot() {
		return (java.math.BigDecimal) getValue(13);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_work_h</code>.
	 */
	public void setHWorkH(java.math.BigDecimal value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_work_h</code>.
	 */
	public java.math.BigDecimal getHWorkH() {
		return (java.math.BigDecimal) getValue(14);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_work_min</code>.
	 */
	public void setHWorkMin(java.math.BigDecimal value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_work_min</code>.
	 */
	public java.math.BigDecimal getHWorkMin() {
		return (java.math.BigDecimal) getValue(15);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_permits_tot</code>.
	 */
	public void setHPermitsTot(java.math.BigDecimal value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_permits_tot</code>.
	 */
	public java.math.BigDecimal getHPermitsTot() {
		return (java.math.BigDecimal) getValue(16);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_permits_h</code>.
	 */
	public void setHPermitsH(java.math.BigDecimal value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_permits_h</code>.
	 */
	public java.math.BigDecimal getHPermitsH() {
		return (java.math.BigDecimal) getValue(17);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_permits_min</code>.
	 */
	public void setHPermitsMin(java.math.BigDecimal value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_permits_min</code>.
	 */
	public java.math.BigDecimal getHPermitsMin() {
		return (java.math.BigDecimal) getValue(18);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_1_tot</code>.
	 */
	public void setHExtra_1Tot(java.math.BigDecimal value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_1_tot</code>.
	 */
	public java.math.BigDecimal getHExtra_1Tot() {
		return (java.math.BigDecimal) getValue(19);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_2_tot</code>.
	 */
	public void setHExtra_2Tot(java.math.BigDecimal value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_2_tot</code>.
	 */
	public java.math.BigDecimal getHExtra_2Tot() {
		return (java.math.BigDecimal) getValue(20);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_2_h</code>.
	 */
	public void setHExtra_2H(java.math.BigDecimal value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_2_h</code>.
	 */
	public java.math.BigDecimal getHExtra_2H() {
		return (java.math.BigDecimal) getValue(21);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_2_min</code>.
	 */
	public void setHExtra_2Min(java.math.BigDecimal value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_2_min</code>.
	 */
	public java.math.BigDecimal getHExtra_2Min() {
		return (java.math.BigDecimal) getValue(22);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_3_tot</code>.
	 */
	public void setHExtra_3Tot(java.math.BigDecimal value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_3_tot</code>.
	 */
	public java.math.BigDecimal getHExtra_3Tot() {
		return (java.math.BigDecimal) getValue(23);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_3_h</code>.
	 */
	public void setHExtra_3H(java.math.BigDecimal value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_3_h</code>.
	 */
	public java.math.BigDecimal getHExtra_3H() {
		return (java.math.BigDecimal) getValue(24);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_extra_3_min</code>.
	 */
	public void setHExtra_3Min(java.math.BigDecimal value) {
		setValue(25, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_extra_3_min</code>.
	 */
	public java.math.BigDecimal getHExtra_3Min() {
		return (java.math.BigDecimal) getValue(25);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_holidays_tot</code>.
	 */
	public void setHHolidaysTot(java.math.BigDecimal value) {
		setValue(26, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_holidays_tot</code>.
	 */
	public java.math.BigDecimal getHHolidaysTot() {
		return (java.math.BigDecimal) getValue(26);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_holidays_h</code>.
	 */
	public void setHHolidaysH(java.math.BigDecimal value) {
		setValue(27, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_holidays_h</code>.
	 */
	public java.math.BigDecimal getHHolidaysH() {
		return (java.math.BigDecimal) getValue(27);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_holidays_min</code>.
	 */
	public void setHHolidaysMin(java.math.BigDecimal value) {
		setValue(28, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_holidays_min</code>.
	 */
	public java.math.BigDecimal getHHolidaysMin() {
		return (java.math.BigDecimal) getValue(28);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_disease_tot</code>.
	 */
	public void setHDiseaseTot(java.math.BigDecimal value) {
		setValue(29, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_disease_tot</code>.
	 */
	public java.math.BigDecimal getHDiseaseTot() {
		return (java.math.BigDecimal) getValue(29);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_disease_h</code>.
	 */
	public void setHDiseaseH(java.math.BigDecimal value) {
		setValue(30, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_disease_h</code>.
	 */
	public java.math.BigDecimal getHDiseaseH() {
		return (java.math.BigDecimal) getValue(30);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_disease_min</code>.
	 */
	public void setHDiseaseMin(java.math.BigDecimal value) {
		setValue(31, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_disease_min</code>.
	 */
	public java.math.BigDecimal getHDiseaseMin() {
		return (java.math.BigDecimal) getValue(31);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_total_tot</code>.
	 */
	public void setHTotalTot(java.math.BigDecimal value) {
		setValue(32, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_total_tot</code>.
	 */
	public java.math.BigDecimal getHTotalTot() {
		return (java.math.BigDecimal) getValue(32);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_total_h</code>.
	 */
	public void setHTotalH(java.math.BigDecimal value) {
		setValue(33, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_total_h</code>.
	 */
	public java.math.BigDecimal getHTotalH() {
		return (java.math.BigDecimal) getValue(33);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_total_min</code>.
	 */
	public void setHTotalMin(java.math.BigDecimal value) {
		setValue(34, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_total_min</code>.
	 */
	public java.math.BigDecimal getHTotalMin() {
		return (java.math.BigDecimal) getValue(34);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_reports_tot</code>.
	 */
	public void setHReportsTot(java.math.BigDecimal value) {
		setValue(35, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_reports_tot</code>.
	 */
	public java.math.BigDecimal getHReportsTot() {
		return (java.math.BigDecimal) getValue(35);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_reports_h</code>.
	 */
	public void setHReportsH(java.math.BigDecimal value) {
		setValue(36, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_reports_h</code>.
	 */
	public java.math.BigDecimal getHReportsH() {
		return (java.math.BigDecimal) getValue(36);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.h_reports_min</code>.
	 */
	public void setHReportsMin(java.math.BigDecimal value) {
		setValue(37, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.h_reports_min</code>.
	 */
	public java.math.BigDecimal getHReportsMin() {
		return (java.math.BigDecimal) getValue(37);
	}

	/**
	 * Setter for <code>drm.timetable_attendancesheet.username</code>.
	 */
	public void setUsername(java.lang.String value) {
		setValue(38, value);
	}

	/**
	 * Getter for <code>drm.timetable_attendancesheet.username</code>.
	 */
	public java.lang.String getUsername() {
		return (java.lang.String) getValue(38);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record2<java.lang.String, java.lang.String> key() {
		return (org.jooq.Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TimetableAttendancesheetRecord
	 */
	public TimetableAttendancesheetRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.TimetableAttendancesheet.TIMETABLE_ATTENDANCESHEET);
	}

	/**
	 * Create a detached, initialised TimetableAttendancesheetRecord
	 */
	public TimetableAttendancesheetRecord(java.lang.String operatorId, java.lang.Short groupId, java.lang.String day, java.math.BigDecimal hWork, java.math.BigDecimal hPermits, java.math.BigDecimal hExtra_1, java.math.BigDecimal hExtra_2, java.math.BigDecimal hExtra_3, java.math.BigDecimal hHolidays, java.math.BigDecimal hDisease, java.math.BigDecimal hTotal, java.lang.Short noLoad, java.math.BigDecimal hReports, java.math.BigDecimal hWorkTot, java.math.BigDecimal hWorkH, java.math.BigDecimal hWorkMin, java.math.BigDecimal hPermitsTot, java.math.BigDecimal hPermitsH, java.math.BigDecimal hPermitsMin, java.math.BigDecimal hExtra_1Tot, java.math.BigDecimal hExtra_2Tot, java.math.BigDecimal hExtra_2H, java.math.BigDecimal hExtra_2Min, java.math.BigDecimal hExtra_3Tot, java.math.BigDecimal hExtra_3H, java.math.BigDecimal hExtra_3Min, java.math.BigDecimal hHolidaysTot, java.math.BigDecimal hHolidaysH, java.math.BigDecimal hHolidaysMin, java.math.BigDecimal hDiseaseTot, java.math.BigDecimal hDiseaseH, java.math.BigDecimal hDiseaseMin, java.math.BigDecimal hTotalTot, java.math.BigDecimal hTotalH, java.math.BigDecimal hTotalMin, java.math.BigDecimal hReportsTot, java.math.BigDecimal hReportsH, java.math.BigDecimal hReportsMin, java.lang.String username) {
		super(com.sonicle.webtop.drm.jooq.tables.TimetableAttendancesheet.TIMETABLE_ATTENDANCESHEET);

		setValue(0, operatorId);
		setValue(1, groupId);
		setValue(2, day);
		setValue(3, hWork);
		setValue(4, hPermits);
		setValue(5, hExtra_1);
		setValue(6, hExtra_2);
		setValue(7, hExtra_3);
		setValue(8, hHolidays);
		setValue(9, hDisease);
		setValue(10, hTotal);
		setValue(11, noLoad);
		setValue(12, hReports);
		setValue(13, hWorkTot);
		setValue(14, hWorkH);
		setValue(15, hWorkMin);
		setValue(16, hPermitsTot);
		setValue(17, hPermitsH);
		setValue(18, hPermitsMin);
		setValue(19, hExtra_1Tot);
		setValue(20, hExtra_2Tot);
		setValue(21, hExtra_2H);
		setValue(22, hExtra_2Min);
		setValue(23, hExtra_3Tot);
		setValue(24, hExtra_3H);
		setValue(25, hExtra_3Min);
		setValue(26, hHolidaysTot);
		setValue(27, hHolidaysH);
		setValue(28, hHolidaysMin);
		setValue(29, hDiseaseTot);
		setValue(30, hDiseaseH);
		setValue(31, hDiseaseMin);
		setValue(32, hTotalTot);
		setValue(33, hTotalH);
		setValue(34, hTotalMin);
		setValue(35, hReportsTot);
		setValue(36, hReportsH);
		setValue(37, hReportsMin);
		setValue(38, username);
	}
}
