package com.example.vlsm_calculator;

public class SubnetInputs {
    String subnetName;
    String subnetHost;

    public SubnetInputs(String subnetName, String subnetHost) {
        this.subnetName = subnetName;
        this.subnetHost = subnetHost;
    }

    public String getSubnetName() {
        return subnetName;
    }

    public void setSubnetName(String subnetName) {
        this.subnetName = subnetName;
    }

    public String getSubnetHost() {
        return subnetHost;
    }

    public void setSubnetHost(String subnetHost) {
        this.subnetHost = subnetHost;
    }
}
