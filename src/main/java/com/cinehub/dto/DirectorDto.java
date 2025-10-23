package com.cinehub.dto;

public class DirectorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
    private String birthDate; // ISO string, keep simple for DTO
    private String biography;

    public DirectorDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
}
