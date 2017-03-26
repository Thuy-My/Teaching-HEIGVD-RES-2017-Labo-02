package ch.heigvd.res.labs.roulette.net.client;

import ch.heigvd.res.labs.roulette.data.EmptyStoreException;
import ch.heigvd.res.labs.roulette.data.JsonObjectMapper;
import ch.heigvd.res.labs.roulette.net.protocol.RouletteV1Protocol;
import ch.heigvd.res.labs.roulette.data.Student;
import ch.heigvd.res.labs.roulette.net.protocol.InfoCommandResponse;
import ch.heigvd.res.labs.roulette.net.protocol.RandomCommandResponse;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the client side of the protocol specification (version 1).
 * 
 * @author Olivier Liechti
 */
public class RouletteV1ClientImpl implements IRouletteV1Client {

  private static final Logger LOG = Logger.getLogger(RouletteV1ClientImpl.class.getName());
  private Socket clientSocket;

  @Override
  public void connect(String server, int port) throws IOException {
    clientSocket = new Socket(server, port);

    InputStream is = clientSocket.getInputStream();
    OutputStream os = clientSocket.getOutputStream();

    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

  }

  @Override
  public void disconnect() throws IOException {
    clientSocket.close();
  }

  @Override
  public boolean isConnected() {
    return clientSocket.isConnected();
  }

  @Override
  public void loadStudent(String fullname) throws IOException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void loadStudents(List<Student> students) throws IOException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Student pickRandomStudent() throws EmptyStoreException, IOException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int getNumberOfStudents() throws IOException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public String getProtocolVersion() throws IOException {
    return RouletteV1Protocol.VERSION;
  }



}
