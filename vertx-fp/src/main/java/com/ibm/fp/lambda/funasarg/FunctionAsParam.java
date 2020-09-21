package com.ibm.fp.lambda.funasarg;

@FunctionalInterface
interface Handler {
    void handle();
}

@FunctionalInterface
interface HttpHandler {
    void handle(Object payload);
}

@FunctionalInterface
interface DatabaseHandler {
    boolean connect(String connectionString);
}

@FunctionalInterface
interface Resolve {
    void resolve(Object response);
}

@FunctionalInterface
interface Reject {
    void reject(Throwable error);
}

class SocketHandler {
    //this takes function as parameter
    public void requestHandler(Handler handler) {
        //invoke lambda function
        handler.handle();

    }
}

class HttpServer {
    public void requestHandler(HttpHandler httpHandler) {
        //response
        String response = "Hello,Im Response";
        httpHandler.handle(response);
    }
}

class Database {
    public void connect(DatabaseHandler handler) {
        boolean status = handler.connect("localhost;4222;dbname=sample");
        if (status) {
            System.out.println("Data base is ready");
        } else {
            System.out.println("Data base not ready");
        }
    }
}

class Login {
    public void validate(Resolve resolver, Reject rejector) {
        //bi logic
        String userName = "admin";
        String password = "admin";
        if (userName.equals("admin") && password.equals("admin")) {
            resolver.resolve("Login Success");
        } else {
            rejector.reject(new RuntimeException("Login Failed"));
        }
    }
}


public class FunctionAsParam {
    public static void main(String[] args) {

        Handler handler = () -> System.out.println("Socket Handler");
        //Socket Object
        SocketHandler socketHandler = new SocketHandler();
        //function as parameter
        socketHandler.requestHandler(handler);
        //function as parameter ; inline arg
        socketHandler.requestHandler(() -> System.out.println("Socket Handler inline"));
        ////////////////////////////////////////////////////////////////////////////////////
        HttpHandler httpHandler = response -> System.out.println(response);
        HttpServer httpServer = new HttpServer();
        httpServer.requestHandler(httpHandler);
        //function as parameter ; inline arg
        httpServer.requestHandler(response -> System.out.println(response));
        //////////////////////////////////////////////////////////////////////////////////////
        DatabaseHandler dbHandler = connectionString -> {
            System.out.println("Database connection");
            return true;
        };
        Database database = new Database();
        database.connect(dbHandler);
        //inline
        database.connect(connectionString -> {
            System.out.println("Database connection");
            return true;
        });

        Login login = new Login();

        login.validate(response -> {
            System.out.println(response);
        }, error -> {
            System.out.println(error);
        });
        login.validate(response -> System.out.println(response), error -> System.out.println(error));

    }
}
