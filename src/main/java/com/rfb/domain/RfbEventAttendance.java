package com.rfb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RfbEventAttendance.
 */
@Entity
@Table(name = "rfb_event_attendance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RfbEventAttendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @ManyToOne
    private RfbEvent rfbEvent;

    @OneToOne
    @JoinColumn(unique = true)
    private RfbUser rbfUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public RfbEventAttendance attendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
        return this;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public RfbEvent getRfbEvent() {
        return rfbEvent;
    }

    public RfbEventAttendance rfbEvent(RfbEvent rfbEvent) {
        this.rfbEvent = rfbEvent;
        return this;
    }

    public void setRfbEvent(RfbEvent rfbEvent) {
        this.rfbEvent = rfbEvent;
    }

    public RfbUser getRbfUser() {
        return rbfUser;
    }

    public RfbEventAttendance rbfUser(RfbUser rfbUser) {
        this.rbfUser = rfbUser;
        return this;
    }

    public void setRbfUser(RfbUser rfbUser) {
        this.rbfUser = rfbUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RfbEventAttendance rfbEventAttendance = (RfbEventAttendance) o;
        if (rfbEventAttendance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rfbEventAttendance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RfbEventAttendance{" +
            "id=" + getId() +
            ", attendanceDate='" + getAttendanceDate() + "'" +
            "}";
    }
}
