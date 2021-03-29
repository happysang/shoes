package mgr;

import java.util.Scanner;

public interface Manageable<T> {
    T read(Scanner scan);
    void print();
    boolean matches(String kwd);
}
