package tourgen.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RepositoryIoManager extends java.util.Observable implements Serializable {

  public RepositoryIoManager() {

  }

  /**
   * Load the repository from a persistent storage.
   */
  public static Repository loadRepository(String fileName) {
    ObjectInputStream inputFile;
    try {
      File input = new File(fileName);
      inputFile = new ObjectInputStream(new FileInputStream(input));
      try {
        Repository newRepository = (Repository) inputFile.readObject();
        inputFile.close();
        return newRepository;
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      /* file not found exception will be skipped */
      /* data will be initialized from text file */
      // e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Save the current repository to persistent storage.
   */
  public static void saveRepository(String fileName, Repository repository) {
    try {
      File output = new File(fileName);
      ObjectOutputStream outputFile = new ObjectOutputStream(new FileOutputStream(output));
      try {
        outputFile.writeObject(Repository.getInstance());
        outputFile.close();
      } catch (IOException i) {
        i.printStackTrace();
      }
      outputFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Load a school manager instance up from a file name.
   * @param fileName the file name of the school manager's persistent data store.
   * @return an instance of SchoolManager
   */
  public static SchoolManager loadSchoolManager(String fileName) {
    ObjectInputStream inputFile;
    try {
      File input = new File(fileName);
      inputFile = new ObjectInputStream(new FileInputStream(input));
      try {
        SchoolManager newManager = (SchoolManager) inputFile.readObject();
        inputFile.close();
        return newManager;
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      /* file not found exception will be skipped */
      /* data will be initialized from text file */
      // e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * 
   * @param fileName
   * @param schoolmanager
   */
  public static void saveSchoolManager(String fileName, SchoolManager schoolmanager) {
    try {
      File output = new File(fileName);
      ObjectOutputStream outputFile = new ObjectOutputStream(new FileOutputStream(output));
      try {
        outputFile.writeObject(schoolmanager);
        outputFile.close();
      } catch (IOException i) {
        i.printStackTrace();
      }
      outputFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * This is a convenient method to load everything up from files stored on disk.
   * @return a schoolManager
   */
  public static SchoolManager loadEverythingUp() {
    SchoolManager manager = loadSchoolManager("schoolManager.bin");
    if (manager != null) {
      Repository.getInstance().setBoyList(manager.getRepository().getBoyList());
      Repository.getInstance().setGirlList(manager.getRepository().getGirlList());
    }
    return manager;
  }
}