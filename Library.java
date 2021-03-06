/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package demo;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import contracts.DDNS;

public class Library {
	
	public static void main(String[] args) {
                String api_url = "";
		
		BigInteger privateKey = readPrivateKeyFromFile("key");

		ECKeyPair ecKey = ECKeyPair.create(privateKey);
		
		Credentials cred = Credentials.create(ecKey);
		
		Web3j client = Web3j.build(new HttpService());
		
		
		try {
			//DDNS c = DDNS.deploy(client, cred, new DefaultGasProvider(), "keer4n").send();
			String contractAddress = "0x6a652d983fe2315ac9d1d59b5221b8b784489425";
			
			DDNS c = DDNS.load(contractAddress, client, cred, new DefaultGasProvider());
			
			System.out.println("Contract address: " + c.getContractAddress());
			
			ECKeyPair ecKey2 = Keys.createEcKeyPair();
			Credentials cred2 = Credentials.create(ecKey2);
			System.out.println("private key: " + ecKey2.getPrivateKey());
			
			TransactionReceipt t = c.register("anu", cred2.getAddress()).send();
			System.out.println(" Transaction: " + t.getTransactionHash());
			
			System.out.println("Query for anu" + c.lookup("anu").send());
			System.out.println("Query for keer4n" + c.lookup("keer4n").send());
			
		//	System.out.println("Owner of the contract "+ c.owner().send());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    public static BigInteger readPrivateKeyFromFile(String fileName) throws FileNotFoundException {
		File f = new File("./" + fileName);
		Scanner scan = new Scanner(f);
		String ret = scan.nextLine();
		scan.close();
		return new BigInteger(ret);
	}
}
