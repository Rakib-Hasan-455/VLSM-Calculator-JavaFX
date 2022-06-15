package com.example.vlsm_calculator;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;
import java.util.stream.Collectors;

public class VLSMController {
    public int count = 0, mod;
    public ArrayList<VLSM> vlsmArrayList = new ArrayList<>();
    public HashMap<String, Integer> hNameValue = new HashMap<>();
    public String subnetName, subnetHost, usedSpace, netAddress,
            firstAddress, lastAddress, netMask, broadCastAddress;
    public MFXTextField netAddrTF;
    public MFXButton NetAddrBtn;
    public Label step1Label;
    public MFXButton noOfSubnetBtn;
    public MFXTextField noOfSubnetTF;
    public Label step2Label;
    public MFXTextField maskTF;
    public MFXTextField subnetNameTF;
    public MFXTextField subnetHostTF;
    public MFXButton addBtn;
    public Label step3Label;
    public TableView<SubnetInputs> subnetTable;
    public TableColumn<SubnetInputs, String> subnetNameCol;
    public TableColumn<SubnetInputs, String> totalHostCol;
    public MFXButton CalculateBtn;
    
    
    public TableView<VLSM> vlsmCalcTable;
    public TableColumn<VLSM, String> netNameCol;
    public TableColumn<VLSM, String> noOfHostCol;
    public TableColumn<VLSM, String> allocSizeCol;
    public TableColumn<VLSM, String> netAddrCol;
    public TableColumn<VLSM, String> firstAddrCol;
    public TableColumn<VLSM, String> lastAddrCol;
    public TableColumn<VLSM, String> broadCastAddrCol;
    public TableColumn<VLSM, String> maskCol;
    public Label step4Label;

    public void NetAddrBtn(ActionEvent actionEvent) {
        boolean isValid = false;
        if (!netAddrTF.getText().equals("")) {
            isValid = validCheck();
        }
        if(!netAddrTF.getText().equals("") && isValid) {
            step1Label.setText("Valid network\naddress & mask!");
            step1Label.setStyle("-fx-text-fill: green");
            netAddrTF.setAllowEdit(false);
            maskTF.setAllowEdit(false);
            NetAddrBtn.setDisable(true);
        } else {
            step1Label.setText("Invalid network\naddress or mask!");
            step1Label.setStyle("-fx-text-fill: red");
        }
    }

    private boolean validCheck() {
        netAddress = netAddrTF.getText();
        String[] octates = netAddress.split("\\.");
        System.out.println(Arrays.toString(octates));
        int firstOctate = Integer.parseInt(octates[0]);
        int secondOctate = Integer.parseInt(octates[1]);
        int thirdOctate = Integer.parseInt(octates[2]);
        int fourthOctate = Integer.parseInt(octates[3]);
        long totalAddress = (long) ++firstOctate * ++secondOctate * ++thirdOctate * ++fourthOctate;
        long maskTotal = (long) Math.pow(2, Integer.parseInt(maskTF.getText().replace("/", "")));
        System.out.println(totalAddress + " " +maskTotal);
        netAddress = "";
        if ((firstOctate >= 0 && firstOctate <= 255) && (secondOctate >= 0 && secondOctate <= 255) && (thirdOctate >= 0 && thirdOctate <= 255) && (fourthOctate >= 0 && fourthOctate <= 255) && (totalAddress <= maskTotal)) {
            return true;
        }
        return false;
    }

    public void noOfSubnetBtn(ActionEvent actionEvent) {
        if(!noOfSubnetTF.getText().equals("")) {
        step2Label.setText("Done !");
        step2Label.setStyle("-fx-text-fill: green");
        count = Integer.parseInt(noOfSubnetTF.getText());
        mod = count;
        noOfSubnetTF.setAllowEdit(false);
        noOfSubnetBtn.setDisable(true);
        } else {
            step2Label.setText("Invalid!");
            step2Label.setStyle("-fx-text-fill: red");
        }
    }

    public void addBtn(ActionEvent actionEvent) {
        // n of subnet = treehmap -> disable add btn
        // set text = done
        if (count != 0 && !noOfSubnetTF.getText().equals("")) {
            if (subnetNameTF.getText().equals("") || subnetHostTF.getText().equals("")) {
                step3Label.setText("Invalid!");
                step3Label.setStyle("-fx-text-fill: red");
            } else {
                String subnetName = subnetNameTF.getText();
                String subnetHost = subnetHostTF.getText();
                hNameValue.put(subnetName, Integer.parseInt(subnetHost));
                // sort map by value
                hNameValue = hNameValue.entrySet().stream().sorted(HashMap.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
                hasmmapTableView();
                count--;
                step3Label.setText(""+(count%mod));
                step3Label.setStyle("-fx-text-fill: green");
                subnetNameTF.clear();
                subnetHostTF.clear();
                if (count == 0) {
                    step3Label.setText("Done !");
                    step3Label.setStyle("-fx-text-fill: green");
                    subnetNameTF.setAllowEdit(false);
                    subnetHostTF.setAllowEdit(false);
                    addBtn.setDisable(true);
                }
            }
        } else {
            step3Label.setText("Invalid!");
            step3Label.setStyle("-fx-text-fill: red");
        }
        subnetNameTF.clear();
        subnetHostTF.clear();

    }


    private void hasmmapTableView() {
        subnetNameCol.setCellValueFactory(new PropertyValueFactory<SubnetInputs, String>("subnetName"));
        totalHostCol.setCellValueFactory(new PropertyValueFactory<SubnetInputs, String>("subnetHost"));

         ObservableList<SubnetInputs> TABLEROW = FXCollections.observableArrayList();
        for (String key : hNameValue.keySet()) {
            SubnetInputs subnetInputs = new SubnetInputs(key, hNameValue.get(key)+"");
            TABLEROW.add(subnetInputs);
        }
        subnetTable.getItems().setAll(TABLEROW);
    }

    public void CalculateBtn(ActionEvent actionEvent) {
        if(!noOfSubnetTF.getText().equals("") && !hNameValue.isEmpty()) {
            netNameCol.setCellValueFactory(new PropertyValueFactory<VLSM, String>("netName"));
            noOfHostCol.setCellValueFactory(new PropertyValueFactory<VLSM, String>("netSize"));
            allocSizeCol.setCellValueFactory(new PropertyValueFactory<VLSM, String>("allocatedSize"));
            netAddrCol.setCellValueFactory(new PropertyValueFactory<VLSM, String>("netAddress"));
            firstAddrCol.setCellValueFactory(new PropertyValueFactory<VLSM, String>("firstAddress"));
            lastAddrCol.setCellValueFactory(new PropertyValueFactory<VLSM, String>("lastAddress"));
            broadCastAddrCol.setCellValueFactory(new PropertyValueFactory<VLSM, String>("broadcastAddress"));
            maskCol.setCellValueFactory(new PropertyValueFactory<VLSM, String>("mask"));

            ObservableList<VLSM> TABLEROW = FXCollections.observableArrayList();

            netAddress = netAddrTF.getText();
            for (String key : hNameValue.keySet()) {
                String NetworkAddress = netAddress;
                String[] octates = netAddress.split("\\.");
                System.out.println(Arrays.toString(octates));
                int firstOctate = Integer.parseInt(octates[0]);
                int secondOctate = Integer.parseInt(octates[1]);
                int thirdOctate = Integer.parseInt(octates[2]);
                int fourthOctate = Integer.parseInt(octates[3]);
                netAddress = "";
                int usedBits = usedBitsCalc(hNameValue.get(key));
                int mask = 32 - usedBits;
                int octate = detectOctate(mask);
                int increment = (int) Math.pow(2, usedBits % 8);
                if (octate == 1) {
                    firstOctate = firstOctate + increment;
                } else if (octate == 2) {
                    secondOctate = secondOctate + increment;
                } else if (octate == 3) {
                    thirdOctate = thirdOctate + increment;
                } else if (octate == 4) {
                    fourthOctate = fourthOctate + increment;
                }
                netAddress = netAddress + firstOctate;
                netAddress = netAddress + ".";
                netAddress = netAddress + secondOctate;
                netAddress = netAddress + ".";
                netAddress = netAddress + thirdOctate;
                netAddress = netAddress + ".";
                netAddress = netAddress + fourthOctate;
                String nextAddress = netAddress;
                String firstAddress = firstAddress(NetworkAddress);
                String broadcastAddress = broadAddress(netAddress, octate);
                String lastAddress = lastAddresss(broadcastAddress, octate + 1);
                System.out.println();
                System.out.println();
                System.out.println("Subnet name: " + key + " Host = " + hNameValue.get(key));
                System.out.println("Net Address: " + NetworkAddress);
                System.out.println("Used Bits: " + usedBits);
                System.out.println("Mask: " + mask);
                System.out.println("Used Space: " + ((int) Math.pow(2, usedBits)));
                System.out.println("Octate incremented: " + octate + " no");
                System.out.println("Increment by: " + increment);
                System.out.println("First Address: " + firstAddress);
                System.out.println("BroadCast Address: " + broadcastAddress);
                System.out.println("Last Address: " + lastAddress);
                System.out.println("Next address: " + nextAddress);
                VLSM vlsm = new VLSM(key, hNameValue.get(key) + "", (int) Math.pow(2, usedBits) + "", netAddress, firstAddress, lastAddress, broadcastAddress, "/" + mask);
                TABLEROW.add(vlsm);
            }
            step4Label.setText("Done !");
            step4Label.setStyle("-fx-text-fill: green");
            vlsmCalcTable.getItems().setAll(TABLEROW);
            CalculateBtn.setDisable(true);
        }
    }

    private int detectOctate(int mask) {
        if (mask >= 1 && mask <= 8) {
            return 1;
        } else if (mask >= 9 && mask <= 16) {
            return 2;
        } else if (mask >= 17 && mask <= 24) {
            return 3;
        } else if (mask >= 25 && mask <= 32) {
            return 4;
        }
        return -1;
    }

    private int usedBitsCalc(int hostNum) {
        int needSpace = 0, i;
        for (i = 1; i <= 32; i++) {
            needSpace = (int) Math.pow(2, i);
            if (hostNum < needSpace) {
                break;
            }
        }
        return i;
    }

    private String lastAddresss(String netAddress, int octate) {
        String[] octates = netAddress.split("\\.");
        System.out.println(Arrays.toString(octates));
        int firstOctate = Integer.parseInt(octates[0]);
        int secondOctate = Integer.parseInt(octates[1]);
        int thirdOctate = Integer.parseInt(octates[2]);
        int fourthOctate = Integer.parseInt(octates[3]);
        netAddress = "";

        for (int i = octate; i <= 4; i++) {
            if (i == 2) {
                secondOctate = secondOctate - 1;
            } else if (i == 3) {
                thirdOctate = thirdOctate - 1;
            } else if (i == 4) {
                fourthOctate = fourthOctate - 1;
            }

        }
        netAddress = netAddress + firstOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + secondOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + thirdOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + fourthOctate;
        return netAddress;
    }

    private String broadAddress(String netAddress, int octate) {
        String[] octates = netAddress.split("\\.");
        System.out.println(Arrays.toString(octates));
        int firstOctate = Integer.parseInt(octates[0]);
        int secondOctate = Integer.parseInt(octates[1]);
        int thirdOctate = Integer.parseInt(octates[2]);
        int fourthOctate = Integer.parseInt(octates[3]);
        netAddress = "";
        for (int i = octate; i <= 4; i++) {
            if (i == 1) {
                if (firstOctate == 0) {
                    firstOctate = 255;
                }
                firstOctate = firstOctate - 1;
            } else if (i == 2) {
                if (secondOctate == 0) {
                    secondOctate = 255;
                    continue;
                }
                secondOctate = secondOctate - 1;
            } else if (i == 3) {
                if (thirdOctate == 0) {
                    thirdOctate = 255;
                    continue;
                }
                thirdOctate = thirdOctate - 1;
            } else if (i == 4) {
                if (fourthOctate == 0) {
                    fourthOctate = 255;
                    continue;
                }
                fourthOctate = fourthOctate - 1;
            }

        }
        netAddress = netAddress + firstOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + secondOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + thirdOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + fourthOctate;
        return netAddress;
    }

    private String firstAddress(String netAddress) {
        String[] octates = netAddress.split("\\.");
        System.out.println(Arrays.toString(octates));
        int firstOctate = Integer.parseInt(octates[0]);
        int secondOctate = Integer.parseInt(octates[1]);
        int thirdOctate = Integer.parseInt(octates[2]);
        int fourthOctate = Integer.parseInt(octates[3]);
        netAddress = "";
        if (fourthOctate != 255) {
            fourthOctate = fourthOctate + 1;
        } else {
            fourthOctate = 0;
        }
        netAddress = netAddress + firstOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + secondOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + thirdOctate;
        netAddress = netAddress + ".";
        netAddress = netAddress + fourthOctate;
        return netAddress;
    }

    public void resetBtn(ActionEvent actionEvent) {
        netAddrTF.setAllowEdit(true);
        netAddrTF.clear();
        maskTF.setAllowEdit(true);
        maskTF.clear();
        noOfSubnetTF.setAllowEdit(true);
        noOfSubnetTF.clear();
        subnetNameTF.setAllowEdit(true);
        subnetNameTF.clear();
        subnetHostTF.setAllowEdit(true);
        subnetNameTF.clear();
        addBtn.setDisable(false);
        noOfSubnetBtn.setDisable(false);
        NetAddrBtn.setDisable(false);
        step1Label.setText("");
        step2Label.setText("");
        step3Label.setText("");
        subnetTable.getItems().clear();
        vlsmCalcTable.getItems().clear();
        CalculateBtn.setDisable(false);
    }
}