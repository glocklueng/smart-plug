/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import DAO.CustomerDao;
import DAO.PricesDao;
import DAO.TransactionDao;
import Model.Customer;
import Model.Transaction;
import SerialCommunication.FrameEvent;
import SerialCommunication.FrameEventListener;
import SerialCommunication.Packet;
import SerialCommunication.SerialTransceiver;
import SerialCommunication.SmartPlugPacket;
import java.util.TooManyListenersException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The
 * <code>RFIDEventManagerSimple</code> class is the central controller in the
 * application. It receives requests from the serial port, processes them and
 * transmits the response. The
 * <code>RFIDEventManagerSimple</code> is implemented using the State design
 * pattern.
 *
 * @version 16/02/10
 * @author ibr
 */
public class RFIDEventManagerSimple implements FrameEventListener {

    private SerialTransceiver transmitter;
    private String portNumber = "COM5";//
    private String source = "34";
    private String destination = "12";
    private Packet packet;
    private String cardIDff;
    private String PINff;
    private String bikeRackIDff;
    private String BikeBrokenff;
    String location;
    String checkIdCommand = "11";
    String idOkCommand="12";
    String idNotOkCommand="13";
    String checkPasswordCommand= "21";
   
    String passswordOkCommand="22";
    String passwordNotOkCommand="23";
    String showBalanceCommand="31";
    String showPricesCommand="41";
    String pricesSentOkCommand="42";
    String transactionCommand="51";
    String transactionOkCommand="52";
    String transactionNotOkCommand="53";
    String getBalanceCommand="61";
Customer customer= null;
    public RFIDEventManagerSimple() {
    }

    /**
     * Get the value of source
     *
     * @return the value of source
     */
    public synchronized String getSource() {
        return source;
    }

    /**
     * Set the value of source
     *
     * @param source new value of source
     */
    public synchronized void setSource(String source) {
        this.source = source;
    }

    /**
     * Get the value of packet
     *
     * @return the value of packet
     */
    public synchronized Packet getPacket() {
        return packet;
    }

    /**
     * Get the value of destination
     *
     * @return the value of destination
     */
    public synchronized String getDestination() {
        return destination;
    }

    /**
     * Set the value of destination
     *
     * @param destination new value of destination
     */
    public synchronized void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Set the value of transmitter
     *
     * @param transmitter new value of transmitter
     */
    public synchronized void setTransmitter(SerialTransceiver transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * Open the transmitter serial port.
     *
     * @throws TooManyListenersException
     */
    public synchronized void openPort() throws TooManyListenersException {
        if (transmitter != null) {
            transmitter.openPort(portNumber);
        }
    }

    /**
     * Close the transmitter serial port.
     */
    public synchronized void closePort() {
        if (transmitter != null) {
            transmitter.closePort();
        }
    }

    /**
     * Send the
     * <code>RFIDResponse</code> as an
     * <code>SmartPlugPacket</code> using the serial transmitter.
     *
     * @param status The status response to send
     * @param data The specific data for the status response
     */
    public synchronized void sendRFIDResponse(String status, String data) {
        SmartPlugPacket responsePacket = new SmartPlugPacket(source, destination, status, data);
        transmitter.transmit(responsePacket.getBytes());
    }

    /**
     * The method called by the
     * <code>SerialFrame</code> when a complete data packet is received.
     *
     * @param frameEvent the frame event
     */
    @Override
    public synchronized void frameReady(FrameEvent frameEvent) {    // Here we are getting info from AVR.
        byte[] received = frameEvent.getData();
        System.out.print("\nReceived at Server: [");
        System.out.println(new String(received) + "]");
        packet = new SmartPlugPacket(received);
        System.out.println("           command: [" + packet.getCommandStatus() + "]");
        System.out.println("           data:    [" + packet.getData() + "]");
        //TO DO Process request and send response

      

       

        
       


        processRequest(packet);
    }

    private void processRequest(Packet packet) { // Here simply we are reponding info to AVR.
        //THIS CODE IS FOR SIMPLE DEMONSTRATION ONLY.
        //IT IS DIFFICULT TO MAINTAIN AND TEST.
        //IT SHOULD BE REPLACED BY EG. COMMAND PATTERN IN THE LATER DESIGN

       
         //Here we are taking info about PIN if correct od no to this class.

        CustomerDao customerDao= new CustomerDao();
        TransactionDao transactionDao =new TransactionDao();
        PricesDao pricesDao= new PricesDao();
        
        System.out.println();
        String command = packet.getCommandStatus();
        System.out.println("Command: " + command);
        if (command.equals(checkIdCommand))
        {    String cardID = packet.getData();
            String cardid = cardID.substring(0, 15);
            System.out.println("Card ID from terminal: " + cardid);
            System.out.println();
            this.cardIDff = cardid;
            
                try {
                if (customerDao.findCustomerById(cardIDff)!=null);
                customer= customerDao.findCustomerById(cardIDff);
                }
                 catch (Error e)
                {
                    e.printStackTrace();
                }
            
            if (customer==null){
                sendRFIDResponse(idNotOkCommand, "notOk" );
                System.out.println("Id not ok");
            }
            else
            {
                sendRFIDResponse(idOkCommand, "okCommand" );
                System.out.println("Id ok");
            }
        }
        
        if (command.equals(checkPasswordCommand))
        {   
            System.out.println();
            String PIN = packet.getData();
            String pin = PIN.substring(16, 20);
            System.out.println("PIN from terminal: " + pin);
            this.PINff = pin;
            if (PINff.equals(customer.getPassword()))
            {
                sendRFIDResponse(passswordOkCommand, "passwordOk" );
                System.out.println("Password ok");
            }
            else
            {
                sendRFIDResponse(passwordNotOkCommand, "passwordNotOk" );
                System.out.println("Password not ok");
            }
        }
        
        if (command.equals(showPricesCommand))
        {
            System.out.println("Show prices command");
            String data = packet.getData(); 
             location= data.substring(0,16);
            location=location.replaceAll("\\s","");
            System.out.println(location);
            System.out.println(pricesDao.findPrices(location).get(0).getPrice_day());
            double priceDay=pricesDao.findPrices(location).get(0).getPrice_day();
            double priceNight=pricesDao.findPrices(location).get(0).getPrice_night();
            String prices= priceDay+ "-"+priceNight;
            sendRFIDResponse(pricesSentOkCommand,prices);
            
        }
        
        if (command.equals(transactionCommand))
        {   String data = packet.getData().substring(0,16); 
            double value = Double.parseDouble(data);
            System.out.println(value+"price");
            
            int idMax= transactionDao.findTransactions().get(transactionDao.findTransactions().size()-1).getTransactionID();
            System.out.println(idMax);
            Calendar cal = Calendar.getInstance();
            cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
            System.out.println( sdf.format(cal.getTime()) );
            String locationS= pricesDao.findPrices(location).get(0).getLocation();
            customer.setBalance(customer.getBalance()-value);
            customerDao.updateCustomerBalance(customer.getBalance(), customer.getId());
            
            if(transactionDao.addTransaction(new Transaction(idMax+1,customer.getId(),value,sdf.format(cal.getTime()),locationS,"Samsung",120))>0)
            sendRFIDResponse(transactionOkCommand,"transactionOk");
            else
                sendRFIDResponse(transactionNotOkCommand,"transactionNotOk");
        }
        
        if (command.equals(getBalanceCommand))
        {   
            String balance= customer.getBalance()+"";
            System.out.println("Get Balance" + balance);
            sendRFIDResponse(getBalanceCommand,balance);
        }
        
       
               
        
//        if ((command.equals("10")) && ("1".equals(status)) && (statusRent.equals("ok"))) {
//            sendRFIDResponse("12", "11");   // This is sending info to AVR and AVR will display on the screen "PIN is correct and Have a nice day".
//            step1.rentBike(cardIDff, bikeRackIDff);
//            step1.blinkTime(cardIDff);
//        }
//        else if((command.equals("10")) && ("0".equals(status))){
//            sendRFIDResponse("12", "02");
//        }     
//        else if ((command.equals("11")) && ("1".equals(status)) && (statusReturn.equals("ok"))) {
//            sendRFIDResponse("12", "11");
//            step1.returnBike(cardIDff, bikeRackIDff, BikeBrokenff);//Returning bike and summing how much money customer has to pay.
//        }
//        else if((command.equals("11")) && ("0".equals(status))){
//            sendRFIDResponse("12", "02");
//        }     
//        else {
//            sendRFIDResponse("12", "20");
//            System.out.println("Error! RUN ! It will explode. :D Have a nice day.");
//            
//        }

    }
}
