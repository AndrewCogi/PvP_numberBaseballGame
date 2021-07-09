
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//792 591
public class GameServer {
   // Person���� ������ ������ Set (���Ͽ��� �о�� ��. ��� �о���� ������ ready�� �ȴ�.)
   public static Set<Person> players = new HashSet<>();
   // ������ �̸�.
   public static String fileName = "person.txt";
   // �α��� �� id�� ������ ������ Set (���Ͽ��� �о�� ��. ��� �о���� ������ ready�� �ȴ�.)
   public static Set<String> ids = new HashSet<>();
   // pw�� ������ ������ Set (���Ͽ��� �о�� ��. ��� �о���� ������ ready�� �ȴ�.)
   public static Set<String> pws = new HashSet<>();
   // ä�ÿ��� ����� ids
   public static Set<String> ids_chat = new HashSet<>();
   // speaker�� person_id�� ���� ��.(key : print writer / value : name)
   public static HashMap<PrintWriter, String> writers = new HashMap<PrintWriter, String>();
   // ��ȣȭ
   private static Encoder encoder = Base64.getEncoder();
   // ���� ������
   public static NumberBaseBall manager = new NumberBaseBall();

   // ���ӿ� ���� num(answer)
   public static int num = manager.makeNewNum();

   // ready�� ���� ����� ��.(2���Ǹ� ���ӽ���.)
   public static int ready = 0;

   @SuppressWarnings("resource")
   public static void main(String[] args) throws Exception {
      // ���Ͽ� �����ؼ� �о�� stream ����.
      ObjectInputStream inputStream = null;

      try {
         // inputStream ����.
         inputStream = new ObjectInputStream(new FileInputStream(new File(GameServer.fileName)));
      } catch (EOFException e) {
         // nothing
      }

      // Person �о �ӽ� ������ ��ü ����.
      Person reader = null;
      try {
         // ��� Person �о����.(���� ���� �� ����)
         while ((reader = (Person) inputStream.readObject()) != null) {
            // players�� Person ��ü ���� �߰�.
            players.add(reader);
            // ��ȣȭ�ϱ�
            //byte[] targetBytes = reader.getPw().getBytes();
            //byte[] encodedBytes = encoder.encode(targetBytes);
            //String password = new String(encodedBytes);
            pws.add(reader.getPw());
         }
      } catch (Exception e) {
         // nothing to do
      }

      // ready
      System.out.println("COMPLETE : READ INFO FROM FILE.");
      System.out.println("Server Ready!");
      System.out.println("Help? -> \\?");

      // thread to command
      new Thread(new Runnable() {
         public void run() {
            Scanner sc = new Scanner(System.in);
            String str;
            while (true) {
               str = sc.nextLine();
               // help
               if (str.equalsIgnoreCase("/help") || str.equalsIgnoreCase("/?")) {
                  System.out.println("==============================");
                  System.out.println("/show : show registered players");
                  System.out.println("/stop : stop server & save information");
                  System.out.println("/state : online | in chat | on game");
                  System.out.println("/add Name Id PW : add new player");
                  System.out.println("==============================");
               }

               // �¶����� ���, ä��â�� �ִ� ���, ���Ӹ���� ����� ���� �����ش�.
               if (str.equalsIgnoreCase("/state")) {
                  System.out.println("============ONLINE============");
                  for (String p : ids) {
                     if (p.equalsIgnoreCase(""))
                        continue;
                     System.out.println("Person Id: " + p);
                  }
                  System.out.println("============INCHAT============");
                  for (String p : ids_chat) {
                     if (p.equalsIgnoreCase(""))
                        continue;
                     System.out.println("Person Id: " + p);
                  }
                  System.out.println("============INGAME============");
                  for (String p : ids) {
                     if (p.equalsIgnoreCase(""))
                        continue;
                     else {
                        if (!ids_chat.contains(p)) {
                           System.out.println("Person Id: " + p);
                        }
                     }
                  }
                  System.out.println("==============================");
               }

               // ���� ����Ǿ��ִ� person�� �����ֱ�
               if (str.equalsIgnoreCase("/show")) {
                  System.out.println("===========DATABASE===========");
                  for (Person p : players) {
                     System.out.println("Person : " + p);
                  }
                  System.out.println("==============================");
               }
               // �����ϰ� �����ϱ�
               if (str.equalsIgnoreCase("/stop")) {
                  ObjectOutputStream outputStream = null;
                  try {
                     // ���Ͽ� ������ outputStream ����.
                     outputStream = new ObjectOutputStream(new FileOutputStream(new File(GameServer.fileName)));
                     // players�� ����� ��� Person ���� ����.
                     for (Person p : players) {
                        outputStream.writeObject(p);
                     }
                     // �Ϸ� Ȯ��.
                     System.out.println("==============================");
                     System.out.println("SAVE COMPLETE.");
                     System.out.println("==============================");
                     // ����.
                     System.exit(0);
                  } catch (FileNotFoundException e) {
                     e.printStackTrace();
                  } catch (IOException e) {
                     e.printStackTrace();
                  } finally {
                     try {
                        // stream close.
                        outputStream.close();
                     } catch (IOException e) {
                        e.printStackTrace();
                     }
                  }
               }
               
               
               
               if(str.toLowerCase().startsWith("/add")) {
                  String[] splitmessage = str.split(" ");
                  Person newUser = new Person("","","",0,0);
                  newUser.setName(splitmessage[1]);
                  int exist = 0;
                  for (Person p : players) {
                     // ȸ�������Ϸ��� id�� �̹� �����ϴ� id�϶�
                     if (p.getId().contains(splitmessage[2])) {
                        // �����ϴ°� �˷��ֱ�
                        System.out.println("This ID already registered");
                        exist = 1;
                        break;
                     }
                  }
                  if(exist == 0)
                     newUser.setId(splitmessage[2]);
                  else
                     continue;
                  
                  byte[] targetBytes = splitmessage[3].getBytes();
                  byte[] encodedBytes = encoder.encode(targetBytes);
                  String password = new String(encodedBytes);
                  newUser.setPw(password);
                  pws.add(newUser.getPw());
                  System.out.println(newUser.getName() + " " + newUser.getId() + " ");
                  players.add(newUser);
               }

            }

         }
      }).start();

      // Thread 500�� ����.
      ExecutorService pool = Executors.newFixedThreadPool(500);// thread 500�� ����
      // serverSocket ����(port number : 59001)
      try (ServerSocket listener = new ServerSocket(59001)) {
         // ��� �޴´�.
         while (true) {
            // client���� ���� ��û�� ����,
            pool.execute(new Handler(listener.accept())); // �������� ���ϼ������ֱ� with thread
         }

      }

   } // main close.

   private static class Handler implements Runnable {
      private String id; // client�� id�� ������ String
      private Socket socket; // socket
      private Scanner in; // text receiver from Client
      private PrintWriter out; // text sender to Client
      private Person user;

      // constructor
      public Handler(Socket socket) {
         this.socket = socket;
      }

      public void run() {
         try { // login ���� �� �����.

            in = new Scanner(socket.getInputStream()); // Client���� ���� stream
            out = new PrintWriter(socket.getOutputStream(), true); // Client���� ���� stream
            user = new Person("", "", "", 0, 0);

            // check
            System.out.println("Connected: " + socket);

            // ���� �޼����� �ִٸ�,
            while (in.hasNext()) {
               // ���� �޾ƿ���
               String str = in.nextLine();

               // ���� ���� '&'�� ������
               String[] splitMessage = str.split("&");

               // check
               // for (int i = 0; i < splitMessage.length; i++) {
               // System.out.println("ACK: " + splitMessage[i]);
               // }

               // login���Ϸ��Ҷ�
               if (splitMessage[0].toLowerCase().startsWith("login")) {

                  // id�κ�
                  if (splitMessage[2].equalsIgnoreCase("id")) {
                     // players���ִ���Ȯ��
                     int exist = 0;
                     for (Person p : players) {
                        if (p.getId().contains(splitMessage[3])) {
                           // �����Ѵٸ�OK������
                           System.out.println("idOK");
                           out.println("login&id&OK");
                           user.setId(splitMessage[3]);
                           exist = 1;
                           break;
                        }
                     }

                     // ��������������ERROR������
                     if (exist == 0) {
                        System.out.println("idERROR");
                        out.println("login&id&ERROR");
                     }

                  }

                  // pw�κ�
                  else if (splitMessage[2].equalsIgnoreCase("pw")) {
                     // pws���ִ���Ȯ��

                     // encoding�ϱ�
                     byte[] targetBytes = splitMessage[3].getBytes();
                     byte[] encodedBytes = encoder.encode(targetBytes);
                     String pw = new String(encodedBytes);
                     //System.out.println(pw);
                     if (pws.contains(pw)) {
                        // �����Ѵٸ�OK������
                        System.out.println("pwOK");
                        out.println("login&pw&OK");

                        ids.add(user.getId());

                        /**/
                        for (Person p : players) {
                           if (p.getId().contentEquals(user.getId())) {
                              user = p;
                              id = user.getId();
                           }
                        }
                        /**/
                        
                        //���ӳ�¥ �ʱ�ȭ.
                        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss");
                        Date currentTime = new Date ();
                        String mTime = mSimpleDateFormat.format ( currentTime );
                        //System.out.println ( mTime );
                        user.setInTime(mTime);
                     }
                     // ��������������ERROR������
                     else {
                        out.println("login&pw&ERROR");
                     }
                  }

                  // �α����ϴ� ����� ������ ������ ��, login & getPersonInfo & [id]
                  if (splitMessage[1].equalsIgnoreCase("getpersoninfo")) {
                     // System.out.println("�޾���");
                     for (Person p : players) { // ��� �÷��̾�� �߿�,
                        if (p.getId().equals(splitMessage[2])) { /// �� ����� ������, (�־�� ������)
                           // USERINFO & [name] & [id] & [pw] & [win] & [lose]�� ������.
                           out.println("userinfo&" + p.getName() + "&" + p.getId() + "&" + p.getPw() + "&"
                                 + p.getWin() + "&" + p.getLose());
                           break;
                        }
                     }
                  }
               }

               // ȸ�����Ժκ�
               // 'signup'���ν����ϸ�
               if (splitMessage[0].toLowerCase().startsWith("signup")) {
                  // idüũ��ư ������ �� ����
                  if (splitMessage[1].toLowerCase().startsWith("idcheck")) {
                     int exist = 0;
                     for (Person p : players) {
                        // ȸ�������Ϸ��� id�� �̹� �����ϴ� id�϶�
                        if (p.getId().contains(splitMessage[2])) {
                           // �����ϴ°� �˷��ֱ�
                           System.out.println("id�̹�����");
                           out.println("signUp&id&Exists");
                           exist = 1;
                           break;
                        }
                     }

                     // id��밡���Ҷ�
                     if (exist == 0) {
                        System.out.println("id�μ�������");
                        out.println("signUp&id&OK");
                     }
                  }

                  // ȸ�����Լ�����������������
                  else if (splitMessage[1].toLowerCase().startsWith("success")) {
                     // name����
                     if (splitMessage[2].equalsIgnoreCase("name")) {
                        user.setName(splitMessage[3]);
                     }
                     // id����
                     else if (splitMessage[2].equalsIgnoreCase("id")) {
                        user.setId(splitMessage[3]);
                     }
                     // pw����
                     else if (splitMessage[2].equalsIgnoreCase("pw")) {
                        byte[] targetBytes = splitMessage[3].getBytes();
                        byte[] encodedBytes = encoder.encode(targetBytes);
                        String password = new String(encodedBytes);
                        user.setPw(password);
                        out.println("signUp&complete");
                     }
                     // �̻��Ѹ�� �����Ѱ��
                     // else {
                     // System.out.println("Error");
                     // }

                     // ȸ�����Կ� ������ ������ �����ϱ�
                     if (user.getId() != "" && user.getPw() != "" && user.getName() != "") {
                        players.add(user);
                        id = user.getId();
                        // System.out.println(user.getId() + " check");

                     }

                     // encoding�ϱ�
                     //byte[] targetBytes = user.getPw().getBytes();
                     //byte[] encodedBytes = encoder.encode(targetBytes);
                     //String password = new String(encodedBytes);
                     // System.out.println(password);
                     pws.add(user.getPw());

                  }
                  // �̻��Ѹ�� �����Ѱ��
                  // else {
                  // System.out.println("Error");
                  // }

               }
					// �������� ���ٴ� ��û�� ����,
					if (splitMessage[0].toLowerCase().startsWith("gotogame")) {
						// System.out.println("ACK: " + splitMessage[1]);
						// ids_chat���� �� ���� ���ְ� (�ϸ� �ȵ�)
						ids_chat.remove(splitMessage[1]);
						// ��� Ŭ���̾�Ʈ�鿡��
						for (PrintWriter writer : writers.keySet()) {
							// �� ������! �˷��ֱ�
							writer.println("GOTOGAME&" + splitMessage[1]);
						}
						System.out.println(splitMessage[1] + "is gone into game!");
					}

					if (splitMessage[0].equalsIgnoreCase("chat")) {

						if (splitMessage.length < 2)
							continue;
						// �� ��° �޼����� start���,
						if (splitMessage[1].toLowerCase().startsWith("start")) {

							/// chatting �濡 ������ ��
							ids_chat.add(id);
							// System.out.println("SERVER NEW ID: " + id);
							for (String id : ids_chat) {
								if (id.contentEquals(user.getId()))
									continue;
								out.println("SETPLAYER " + id);
							}
							for (PrintWriter writer : writers.keySet()) {
								String ID = writers.get(writer);
								if (!ids_chat.contains(ID)) { // ***ids_chat�ȿ� �� writer�� ���� ����� �ִٸ�, ***
									continue;
								}
								writer.println("MESSAGE " + id + " has joined");
								writer.println("SETPLAYER " + id);
							}
							// writers hash map�� �� client�� writer������ �־��ش�.
							if (!writers.containsValue(id)) {
								writers.put(out, id);
							}
							// check
							// System.out.println("ID : " + user.getId() + " NAME : " + user.getName());

						}

						else if (splitMessage[1].toLowerCase().startsWith("getreco")) {
							// input�� " "������ �ɰ���.
							String[] st = splitMessage[1].split(" ");
							// whisper�ϰ��� �ϴ� ����� nickname�� s_id�� �����Ѵ�.
							String s_id = st[1];
							// check message
							System.out.println(id + " read " + s_id + "'s records");

							for (Person r_user : players) {
								if (s_id.contentEquals(r_user.getId())) {
									String records = "READRECO " + r_user.getId() + " " + r_user.getWin() + " "
											+ r_user.getLose();
									out.println(records);
									break;
								}
							}
						}

						else if (splitMessage[1].toLowerCase().startsWith("rqgame")) {
							// input�� ���⸦ �������� split�Ѵ�.
							String st[] = splitMessage[1].split(" ");
							// ������ nickname�� s_id�� �����Ѵ�.
							String s_id = st[1];
							// check message
							System.out.println(id + " requests game to " + s_id);

							for (PrintWriter writer : writers.keySet()) {
								if (s_id.contentEquals(writers.get(writer))) {
									String records = "REQUESTGAME " + id;
									writer.println(records);
									break;
								}
							}
						}

						else if (splitMessage[1].toLowerCase().startsWith("replyto")) {
							// input�� ���⸦ �������� split�Ѵ�.
							String st[] = splitMessage[1].split(" ");
							// ������ nickname�� s_id�� �����Ѵ�.
							String s_id = st[1];

							for (PrintWriter writer : writers.keySet()) {
								if (s_id.contentEquals(writers.get(writer))) {
									if (st[2].equalsIgnoreCase("yes")) {
										String records = "REPLY " + id + " yes";
										writer.println(records);
									} else {
										String records = "REPLY " + id + " no";
										writer.println(records);
									}
									break;
								}
							}
						}

						// logout�̸�,
						else if (splitMessage[1].toLowerCase().startsWith("/logout")) {
							return;
						}

						// whisper mode
						else if (splitMessage[1].toLowerCase().startsWith("whisper")) {
							String st[] = splitMessage[1].split(" "); // input�� ���⸦ �������� split�Ѵ�.
							String s_id = st[2]; // whisper�ϰ��� �ϴ� ����� nickname�� s_id�� �����Ѵ�.
							System.out.println(id + " whisper to " + s_id); // check message
							// ���� server�� ���� �� user���� �̸� �� s_id�� ��ġ�ϴ� �̸��� ���ٸ� �� ������ ������ �˷��ش�.
							if (!ids.contains(s_id)) {
								out.println("MESSAGE " + s_id + " is not in this server.");
							} else {
								// writers hash map���� value�� ������ ����� �̸�, s_id�� ���� ������� client���� whisper message��
								// ������.
								for (PrintWriter writer : writers.keySet()) {
									if (s_id.contentEquals(writers.get(writer))
											|| id.contentEquals(writers.get(writer))) {
										String p_string = "WHISPER " + id + "(whisper to " + s_id + ") :"; // client����
										for (int i = 4; i < st.length; i++) { // split�� message�� �ϳ��� string���� �����Ѵ�.
											p_string = p_string + " " + st[i];
										}
										writer.println(p_string); // �ش� client���� ������.
									} // close if
								} // close for
							} // close else
						} // close if

						else {
							for (PrintWriter writer : writers.keySet()) {
								writer.println("MESSAGE " + id + ": " + splitMessage[1]);
							} // close for
						} // close else
					}

					// game���� �����ϴ� �����̶��,(���Ӱ���)
					else if (splitMessage[0].equalsIgnoreCase("game")) {

						// ready���!
						if (splitMessage[3].equalsIgnoreCase("ready")) {
							// ready ��Ų�� �����ֱ�.
							for (PrintWriter writer : writers.keySet()) { // receiver�� ���� writerã��,
								if (splitMessage[2].contentEquals(writers.get(writer))) {
									writer.println("READY&" + splitMessage[1]);
									ready++;
									System.out.println("READY = " + ready);
									break;
								}
							}
							// �� �� ready���� Ȯ��.
							if (ready == 2) {
								ready = 0; // init
								// ���� ����! �϶�� �ѿ��� ���!
								System.out.println("Game Start!");
								// �ѿ� ���� writerã�� �����ֱ�. GAMESTART & id1 & id2 (id2 & id1) &
								for (PrintWriter writer : writers.keySet()) {
									// receiver ã��.
									if (splitMessage[1].contentEquals(writers.get(writer))) {
										writer.println(
												"GAMESTART&" + splitMessage[2] + "&" + splitMessage[1] + "&" + num);

									}
									// sender ã��.
									if (splitMessage[2].contentEquals(writers.get(writer))) {
										writer.println(
												"GAMESTART&" + splitMessage[1] + "&" + splitMessage[2] + "&" + num);
									}

								}
								// num �ʱ�ȭ.
								num = manager.makeNewNum();
							}
						}

						// ready Ǭ�Ŷ��!
						else if (splitMessage[3].equalsIgnoreCase("cancel")) {
							// ready Ǯ���� �����ֱ�.
							for (PrintWriter writer : writers.keySet()) { // receiver�� ���� writerã��,
								if (splitMessage[2].contentEquals(writers.get(writer))) {
									writer.println("CANCEL&" + splitMessage[1]);
									ready--;
									break;
								}
							}
						}

						// Game & [sender] & [receiver] & Message & contents
						else if (splitMessage[3].equalsIgnoreCase("message")) {
							// splitMessage[4] (�޼��� ����)�� id2���� �����ֱ�.
							for (PrintWriter writer : writers.keySet()) {
								if (splitMessage[2].contentEquals(writers.get(writer))) {
									// Message & [sender] & content
									writer.println("MESSAGE&" + splitMessage[1] + "&" + splitMessage[4]);
									// System.out.println("SENT!");
									break;
								}
							}
						}

						// Game & [sender] & [receiver] & showInfo
						else if (splitMessage[3].equalsIgnoreCase("showinfo")) {
							// sender���� ������� �����ֱ�.
							for (Person r_user : players) {
								if (splitMessage[2].contentEquals(r_user.getId())) {
									String records = "READRECO&" + r_user.getId() + "&" + r_user.getWin() + "&"
											+ r_user.getLose();

									for (PrintWriter writer : writers.keySet()) {
										if (splitMessage[1].contentEquals(writers.get(writer))) {
											// Message & [sender] & content
											writer.println(records);
											// System.out.println("SENT!");
											break;
										}
									}
									break;
								}
							}
						}
					} // if(game) close.

					// Gaming���� �����ϴ� �����̶��,
					else if (splitMessage[0].equalsIgnoreCase("gaming")) {
						// Message ���,
						if (splitMessage[3].equalsIgnoreCase("message")) {
							// splitMessage[4] (�޼��� ����)�� id2���� �����ֱ�.
							for (PrintWriter writer : writers.keySet()) {
								if (splitMessage[1].contentEquals(writers.get(writer))) {
									// Message & [sender] & content
									writer.println("MESSAGE&" + splitMessage[1] + "&" + splitMessage[4]);
									// System.out.println("SENT!");
									break;
								}
							}
						}
						// GameMessage ���,
						else if (splitMessage[3].equalsIgnoreCase("gamingmessage")) {

							// splitMessage[4] (�޼��� ����)�� id2���� �����ֱ�.
							for (PrintWriter writer : writers.keySet()) {
								// ???????? �� [sender]���� ������ ����� ���°���;
								if (splitMessage[1].contentEquals(writers.get(writer))) {
									// Message & [sender] & content
									// Gaming & [receiver] & [sender] & GamingMessage & Numbers & result
									System.out.println("[receiver]: " + splitMessage[1]);
									// GAMINGMESSAGE & [receiver] & Numbers & result
									writer.println("GAMINGMESSAGE&" + splitMessage[1] + "&" + splitMessage[4] + "&"
											+ splitMessage[5]);
									// System.out.println("SENT!");
									System.out.println("Next Game Answer: " + num);
									break;
								}
							}
						}
						// Gaming ��� ���Ÿ޼������,
						// Gaming & [sender] & GameResult & [win/lose]
						else if (splitMessage[2].equalsIgnoreCase("gameresult")) {
							// ���̶��,
							if (splitMessage[3].equalsIgnoreCase("win")) {
								// [sender]�� win �ϳ� �÷��ֱ�.
								for (Person p : players) {
									// players�� �ִ� ID�� ���� ���� ID�� ���ٸ�,
									if (p.getId().equals(splitMessage[1])) {
										// win �ϳ� ����.
										p.setWin(p.getWin() + 1);
									}
								}
							}
							// �ж��,
							else if (splitMessage[3].equalsIgnoreCase("lose")) {
								// [sender]�� lose �ϳ� �÷��ֱ�.
								for (Person p : players) {
									// players�� �ִ� ID�� ���� ���� ID�� ���ٸ�,
									if (p.getId().equals(splitMessage[1])) {
										// lose�ϳ� ����.
										p.setLose(p.getLose() + 1);
									}
								}
							}
						}

						// �׺��޼������, // gaming & surrender & [me] & [opp]
						else if (splitMessage[1].equalsIgnoreCase("surrender")) {
							System.out.println(splitMessage[2] + " / " + splitMessage[3] + " / " + splitMessage[4]);

						}

					}

				} // close while

				// stream �������� ������ �����,
			} catch (Exception e) {
				// nothing to do
			} finally {
				// ���� out�� null�� �ƴ϶�� 'writers' hash set���� client�� out�� �����.
				if (out != null) {
					writers.remove(out);
				}
				// ���� id�� null�� �ƴ϶�� �ش� client�� �����ٰ� ����ϰ� 'ids' hash set���� client�� �̸��� �����.
				if (id != null) {
					System.out.println(id + " is leaving");
					ids.remove(id);
					ids_chat.remove(id);

					// �������� client�鿡�� �� client�� �����ٴ� �޼����� ������.
					for (PrintWriter writer : writers.keySet()) {
						String ID = writers.get(writer);
						if (!ids_chat.contains(ID)) { // ***ids_chat�ȿ� �� writer�� ���� ����� �ִٸ�, ***
							continue;
						}
						writer.println("MESSAGE " + id + " has left");
						writer.println("OUTPLAYER " + id);
					}
				} // close if
				try {
					socket.close(); // socket�� �ݾ� �ش� client���� ������ �����Ѵ�.
				} catch (IOException e) {
				} // ������ �߻��Ѵٸ� catch�Ѵ�.
			} // close finally
		}// close run

	}
}