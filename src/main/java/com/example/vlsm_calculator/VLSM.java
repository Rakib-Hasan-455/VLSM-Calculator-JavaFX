package com.example.vlsm_calculator;

public class VLSM {
    String netName;
    String netSize;
    String allocatedSize;
    String netAddress;
    String firstAddress;
    String lastAddress;
    String mask;
    String broadcastAddress;

    public VLSM(String netName, String netSize, String allocatedSize, String netAddress, String firstAddress, String lastAddress, String broadcastAddress, String mask) {
        this.netName = netName;
        this.netSize = netSize;
        this.allocatedSize = allocatedSize;
        this.netAddress = netAddress;
        this.firstAddress = firstAddress;
        this.lastAddress = lastAddress;
        this.mask = mask;
        this.broadcastAddress = broadcastAddress;
    }

    public VLSM() {

    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getNetSize() {
        return netSize;
    }

    public void setNetSize(String netSize) {
        this.netSize = netSize;
    }

    public String getAllocatedSize() {
        return allocatedSize;
    }

    public void setAllocatedSize(String allocatedSize) {
        this.allocatedSize = allocatedSize;
    }

    public String getNetAddress() {
        return netAddress;
    }

    public void setNetAddress(String netAddress) {
        this.netAddress = netAddress;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public void setFirstAddress(String firstAddress) {
        this.firstAddress = firstAddress;
    }

    public String getLastAddress() {
        return lastAddress;
    }

    public void setLastAddress(String lastAddress) {
        this.lastAddress = lastAddress;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getBroadcastAddress() {
        return broadcastAddress;
    }

    public void setBroadcastAddress(String broadcastAddress) {
        this.broadcastAddress = broadcastAddress;
    }
}
