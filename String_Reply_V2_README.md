# V2 implementation of reply endpoint

## V2 Endpoint Structure

To use version2, the url structure is as follows:

```
/v2/reply/{message}
```

Where {message} can either be: 

1. An aphanumerical string
E.g. 
    
    ```
        GET /v2/reply/helloworld
    ```

    Returns
    
    ```
        {
            message: helloworld
        }
    ```

2. A string with the following pattern

    ```
    [0-9]+-[a-z0-9]
    ```

 The first part is a sequence of one or more numbers that defines the transform function that needs to be applied in sqquence to the message. 

Currently supported transform functions: 
        
1. reverse of string (denoted as number 1)
    
    E.g. 
    
    ```
        GET /v2/reply/1-helloworld
    ```

    Returns
    
    ```
        {
            message: dlrowolleh
        }
    ```
 2. Encoded message via MD5 hash algorithm and displayed as hex. (denoted as number 2)

    E.g. 
    
    ```
        GET /v2/reply/2-helloworld
    ```

    Returns
    
    ```
        {
            message: fc5e038d38a57032085441e7fe7010b0
        }
    ```
To add more transform functions, refer to java/com/beta/replyservice/Transformer.java, and add more items to the switch case. 

```java
switch(transformer.charAt(i)){
    case '1':
        StringBuilder reversed_message = new StringBuilder();
        reversed_message.append(transformed_message);
        reversed_message.reverse();
        transformed_message = reversed_message.toString();
        break;
    case '2':
        MD5 encrypted_message = new MD5(transformed_message);
        transformed_message = encrypted_message.getMd5();
        break;
    default:
        return "Invalid Input";
}
```

Giving a few examples,

```
GET /v2/reply/11-kbzw9ru
{
    "data": "kbzw9ru"
}
```
```
GET /v2/reply/12-kbzw9ru
{
    "data": "5a8973b3b1fafaeaadf10e195c6e1dd4"
}
```
```
GET /v2/reply/22-kbzw9ru
{
    "data": "e8501e64cf0a9fa45e3c25aa9e77ffd5"
}
```

## V2 Error Handling

1. Using undefined tranform functions (currently supported ones are only 1 and 2) will returna status code of 400 with message "Invalid Input"
    
    E.g. 
    
    ```
        GET /v2/reply/13-helloworld
    ```

    Returns
    
    ```
        {
            message: "Invalid Input"
        }
    ```