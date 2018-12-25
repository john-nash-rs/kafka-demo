package com.wp.npe.db.entities;

import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    /**
     * CREATE TABLE `student` (
     *   `name` varchar(20) DEFAULT NULL,
     *   `student_id` int(10) DEFAULT NULL,
     *   `is_present` tinyint(1) DEFAULT NULL,
     *   `id` int(11) NOT NULL AUTO_INCREMENT,
     */
    private Integer id;
    private String name;
    private Integer isPresent;
    private Integer studentId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_present")
    public Integer getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Integer isPresent) {
        this.isPresent = isPresent;
    }

    @Column(name = "student_id")
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
