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
  private BufferedReader input;
  private PrintWriter output;

  @Override
  public void connect(String server, int port) throws IOException {
    clientSocket = new Socket(server, port);

    input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    output = new PrintWriter(clientSocket.getOutputStream());

    input.readLine();
  }

  @Override
  public void disconnect() throws IOException {
    output.println(RouletteV1Protocol.CMD_BYE);
    output.flush();
    clientSocket.close();
  }

  @Override
  public boolean isConnected() {
    if (clientSocket == null) {
      return false;
    } else {
      return clientSocket.isConnected();
    }
  }

  @Override
  public void loadStudent(String fullname) throws IOException {
    output.println(RouletteV1Protocol.CMD_LOAD);
    output.println(fullname);
    output.println(RouletteV1Protocol.CMD_LOAD_ENDOFDATA_MARKER);
    output.flush();
    input.readLine();
    input.readLine();
  }

  @Override
  public void loadStudents(List<Student> students) throws IOException {
    output.println(RouletteV1Protocol.CMD_LOAD);
    for(Student s : students) {
      output.println(s.toString());
    }
    output.println(RouletteV1Protocol.CMD_LOAD_ENDOFDATA_MARKER);
    output.flush();
    input.readLine();
    input.readLine();
  }

  @Override
  public Student pickRandomStudent() throws EmptyStoreException, IOException {
    output.println(RouletteV1Protocol.CMD_RANDOM);
    output.flush();
    String response = input.readLine();

    if(JsonObjectMapper.parseJson(response, RandomCommandResponse.class).getError() == null) {
      return new Student(JsonObjectMapper.parseJson(response, RandomCommandResponse.class).getFullname());
    } else {
      throw new EmptyStoreException();
    }
  }

  @Override
  public int getNumberOfStudents() throws IOException {
    output.println(RouletteV1Protocol.CMD_INFO);
    output.flush();
    return JsonObjectMapper.parseJson(input.readLine(), InfoCommandResponse.class).getNumberOfStudents();
  }

  @Override
  public String getProtocolVersion() throws IOException {
    output.println(RouletteV1Protocol.CMD_INFO);
    output.flush();
    return JsonObjectMapper.parseJson(input.readLine(), InfoCommandResponse.class).getProtocolVersion();
  }



}
