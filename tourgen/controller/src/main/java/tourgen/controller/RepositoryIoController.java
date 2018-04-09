package tourgen.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import tourgen.model.Repository;
import tourgen.util.IRepositoryView;
import tourgen.model.RepositoryIoManager;

public class RepositoryIoController {

  private Repository repo;
  IRepositoryView rv;

  public RepositoryIoController(IRepositoryView rv) {
    this.repo = Repository.getInstance();
    this.rv = rv;
  }

  public Repository loadRepositoryFromPath(String fileName) {
    return (Repository) RepositoryIoManager.loadRepository(fileName);
  }

  public void saveRepositoryToPath(String fileName) {
    // RepositoryIOManager.saveRepository(fileName,
    // (Repository)Repository.readResolve());
  }
}