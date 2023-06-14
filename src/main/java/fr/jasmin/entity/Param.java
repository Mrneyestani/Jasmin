package fr.jasmin.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "param")
public class Param {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotEmpty
	@Column(name = "algorithm", nullable = false)
	private String algorithm;

	@NotEmpty
	@Column(name = "key_secret", nullable = false)
	private byte[] keyInByte;

	public Param() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public byte[] getKeyInByte() {
		return keyInByte;
	}

	public void setKeyInByte(byte[] keyInByte) {
		this.keyInByte = keyInByte;
	}

	@Override
	public String toString() {
		return "Param [id=" + id + ", algorithm=" + algorithm + ", keyInByte=" + Arrays.toString(keyInByte) + "]";
	}

}
