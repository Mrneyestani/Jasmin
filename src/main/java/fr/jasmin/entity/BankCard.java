package fr.jasmin.entity;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

import fr.jasmin.encryption.algo.EncryptionAlgorithm;

@Entity
@Table(name = "bank_card")
@XmlRootElement
public class BankCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotEmpty
	@Column(name = "name_owner", length = 45, nullable = false)
	private String nameOwner;

	@NotEmpty
	@Column(name = "first_name_owner", length = 45, nullable = false)
	private String firstNameOwner;

	@NotEmpty
	@Column(name = "number", length = 16, nullable = false)
	private byte[] number;

	@Transient
	private String reelNubmer;

	@NotEmpty
	@Column(name = "date_fin_validite", nullable = false)
	private Date dateFinValidite;

	@NotEmpty
	@Column(name = "cryptogram", length = 3, nullable = false)
	private byte[] cryptogram;

	@Transient
	private String reelCryptogram;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public BankCard() {

	}

	public BankCard(@NotEmpty String nameOwner, @NotEmpty String firstNameOwner, @NotEmpty byte[] number,
			@NotEmpty Date dateFinValidite, @NotEmpty byte[] cryptogram) {

		this.nameOwner = nameOwner;
		this.firstNameOwner = firstNameOwner;
		this.number = number;
		this.dateFinValidite = dateFinValidite;
		this.cryptogram = cryptogram;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameOwner() {
		return nameOwner;
	}

	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	public String getFirstNameOwner() {
		return firstNameOwner;
	}

	public void setFirstNameOwner(String firstNameOwner) {
		this.firstNameOwner = firstNameOwner;
	}

	public Date getDateFinValidite() {
		return dateFinValidite;
	}

	public void setDateFinValidite(Date dateFinValidite) {
		this.dateFinValidite = dateFinValidite;
	}

	public byte[] getNumber() {
		return number;
	}

	public void setNumber(byte[] number) {
		this.number = number;
	}

	public String getReelNubmer() {
		return reelNubmer;
	}

	public void setReelNubmer(String reelNubmer) {
		this.reelNubmer = reelNubmer;
	}

	public byte[] getCryptogram() {
		return cryptogram;
	}

	public void setCryptogram(byte[] cryptogram) {
		this.cryptogram = cryptogram;
	}

	public String getReelCryptogram() {
		return reelCryptogram;
	}

	public void setReelCryptogram(String reelCryptogram) {
		this.reelCryptogram = reelCryptogram;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PaiementCard [id=" + id + ", nameOwner=" + nameOwner + ", firstNameOwner=" + firstNameOwner
				+ ", number=" + Arrays.toString(number) + ", dateFinValidite=" + dateFinValidite + ", cryptogram="
				+ Arrays.toString(cryptogram) + "]";
	}

	public void encryptNumber() throws Exception {
		setNumber(EncryptionAlgorithm.encrypt(getReelNubmer()));
	}

	public void decryptNumber() throws Exception {
		setReelNubmer(EncryptionAlgorithm.decrypt(getNumber()));

	}

	public void encryptCryptogram() throws Exception {
		setCryptogram(EncryptionAlgorithm.encrypt(getReelCryptogram()));
	}

	public void decryptCryptogram() throws Exception {
		setReelCryptogram(EncryptionAlgorithm.decrypt(getCryptogram()));

	}

}
