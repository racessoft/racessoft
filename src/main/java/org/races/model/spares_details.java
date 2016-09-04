package org.races.model;

public class spares_details {
String spare_code;
String part_description;
String obsolete;

public String getSpare_code() {
	return spare_code;
}
public void setSpare_code(String spare_code) {
	this.spare_code = spare_code;
}
public String getPart_description() {
	return part_description;
}
public void setPart_description(String part_description) {
	this.part_description = part_description;
}
public String getObsolete() {
	return obsolete;
}
public void setObsolete(String obsolete) {
	this.obsolete = obsolete;
}
public float getCost() {
	return cost;
}
public void setCost(float cost) {
	this.cost = cost;
}
public int getMin_order() {
	return min_order;
}
public void setMin_order(int min_order) {
	this.min_order = min_order;
}
float cost;
int min_order;
}

