package src;

import club.cybet.utils.Constants;
import org.junit.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import src.wrappers.Crowdsale;
import src.wrappers.CyBetToken;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * cybet-api (src)
 * Created by: cybet
 * On: 25 Oct, 2018 10/25/18 10:35 AM
 **/
public class Ethereum {

    private Web3j web3;
    private Credentials credentials;
    private ContractGasProvider gasProvider;

    public Ethereum(){
        this.web3 = Web3j.build(new HttpService(Constants.getInfuraLink()));
        this.gasProvider = new DefaultGasProvider();
        try {
            this.credentials = WalletUtils.loadCredentials(
                    Constants.getEthWalletPassword(),
                    Constants.getEthWalletFilePath()
            );
        } catch (IOException | CipherException e) {
            this.credentials = null;
            e.printStackTrace();
        }
    }

    @Test public void testInfura() throws Exception {

        CyBetToken cyBetToken = CyBetToken.load(
                Constants.CONTRACT_ADDR_CYBET_TOKEN,
                web3, credentials, gasProvider
        );
        Crowdsale crowdsale = Crowdsale.load(
                Constants.CONTRACT_ADDR_CROWDSALE,
                web3, credentials, gasProvider
        );

        BigInteger result = cyBetToken.balanceOf("0x1E554a1786dD070E181D2b0A33633143A3039dcC").send();

        System.out.println(formatCyBetToken(result));
        System.out.println(credentials.getAddress());

    }

    @Test public void deployCyBet() throws Exception {

        CyBetToken cyBetToken = CyBetToken.deploy(
                web3, credentials, gasProvider
        ).send();

        System.out.println("Validity: "+cyBetToken.isValid());

        System.out.println("Address: "+cyBetToken.getContractAddress());

    }

    @Test public void deployCrowdsale() throws Exception {

        Crowdsale crowdsale = Crowdsale.deploy(
                web3, credentials, gasProvider, "0xa221bc3332656ed6f599600d7a075b1c2acab34b"
        ).send();

        System.out.println("Validity: "+crowdsale.isValid());

        System.out.println("Address: "+crowdsale.getContractAddress()); //0x382a896dcc05186befd6baeef41b2a9369eec304

    }


    private String formatCyBetToken(BigInteger value){
        DecimalFormat formatter = new DecimalFormat("#,###.0000");
        return new DecimalFormat("#,###.0000").format(
                value.divide(new BigInteger("1000000000000000000"))) + " CYBT";
    }

}
