package silverspoon;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class Crowdsale extends Contract {
    private static final String BINARY = "608060405260008055600060015534801561001957600080fd5b50604051602080610826833981016040818152915160028054600160a060020a0319908116331790915560048054600160a060020a0380851691909316178082557f8da5cb5b0000000000000000000000000000000000000000000000000000000085529451929490911692638da5cb5b92828201926020928290030181600087803b1580156100a857600080fd5b505af11580156100bc573d6000803e3d6000fd5b505050506040513d60208110156100d257600080fd5b505160038054600160a060020a031916600160a060020a03909216919091179055506004805460a060020a60ff02191690556a14adf4b7320334b9000000600155610704806101226000396000f30060806040526004361061008d5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663355274ea8114610278578063518ab2a81461029f57806372dd52e3146102b457806389311e6f146102e5578063903a3ef6146102fc578063bf58390314610311578063c7876ea414610326578063f851a4401461033b575b600080600160045474010000000000000000000000000000000000000000900460ff1660028111156100bb57fe5b146100c557600080fd5b600034116100d257600080fd5b6001546000106100e157600080fd5b349150610116670de0b6b3a764000061010a8469021e19e0c9bab240000063ffffffff61035016565b9063ffffffff61037f16565b9050806001541015151561012957600080fd5b60005461013c908263ffffffff61039416565b600081905561015d906a14adf4b7320334b90000009063ffffffff6103a116565b60015560048054604080517fa9059cbb00000000000000000000000000000000000000000000000000000000815233938101939093526024830184905251600160a060020a039091169163a9059cbb9160448083019260209291908290030181600087803b1580156101ce57600080fd5b505af11580156101e2573d6000803e3d6000fd5b505050506040513d60208110156101f857600080fd5b5050600254604051600160a060020a039091169083156108fc029084906000818181858888f19350505050158015610234573d6000803e3d6000fd5b506040805133815234602082015280820183905290517f4ae4bd7655e0d350876a23cd90c4227b13db560e34435c6a488150a9c844bf5f9181900360600190a15050005b34801561028457600080fd5b5061028d6103b3565b60408051918252519081900360200190f35b3480156102ab57600080fd5b5061028d6103c2565b3480156102c057600080fd5b506102c96103c8565b60408051600160a060020a039092168252519081900360200190f35b3480156102f157600080fd5b506102fa6103d7565b005b34801561030857600080fd5b506102fa61045b565b34801561031d57600080fd5b5061028d6104b2565b34801561033257600080fd5b5061028d6104b8565b34801561034757600080fd5b506102c96104c6565b600082151561036157506000610379565b5081810281838281151561037157fe5b041461037957fe5b92915050565b6000818381151561038c57fe5b049392505050565b8181018281101561037957fe5b6000828211156103ad57fe5b50900390565b6a14adf4b7320334b900000081565b60005481565b600454600160a060020a031681565b600254600160a060020a031633146103ee57600080fd5b600260045474010000000000000000000000000000000000000000900460ff16600281111561041957fe5b141561042457600080fd5b6004805474ff0000000000000000000000000000000000000000191674010000000000000000000000000000000000000000179055565b600254600160a060020a0316331461047257600080fd5b600260045474010000000000000000000000000000000000000000900460ff16600281111561049d57fe5b14156104a857600080fd5b6104b06104d5565b565b60015481565b69021e19e0c9bab240000081565b600254600160a060020a031681565b600254600160a060020a031633146104ec57600080fd5b6004805474ff000000000000000000000000000000000000000019167402000000000000000000000000000000000000000017808255604080517f8da5cb5b0000000000000000000000000000000000000000000000000000000081529051600160a060020a039092169263a9059cbb928492638da5cb5b92818101926020929091908290030181600087803b15801561058557600080fd5b505af1158015610599573d6000803e3d6000fd5b505050506040513d60208110156105af57600080fd5b505160048054604080517f70a08231000000000000000000000000000000000000000000000000000000008152309381019390935251600160a060020a03909116916370a082319160248083019260209291908290030181600087803b15801561061857600080fd5b505af115801561062c573d6000803e3d6000fd5b505050506040513d602081101561064257600080fd5b5051604080517c010000000000000000000000000000000000000000000000000000000063ffffffff8616028152600160a060020a03909316600484015260248301919091525160448083019260209291908290030181600087803b1580156106aa57600080fd5b505af11580156106be573d6000803e3d6000fd5b505050506040513d60208110156106d457600080fd5b50505600a165627a7a72305820c19363117c149971b5d72ea409b3f20409afdb855319e5091a0af83fe53eee410029";

    public static final String FUNC_CAP = "cap";

    public static final String FUNC_TOKENSSOLD = "tokensSold";

    public static final String FUNC_COINCONTRACT = "coinContract";

    public static final String FUNC_STARTICO = "startIco";

    public static final String FUNC_FINALIZEICO = "finalizeIco";

    public static final String FUNC_REMAININGTOKENS = "remainingTokens";

    public static final String FUNC_BASEPRICE = "basePrice";

    public static final String FUNC_ADMIN = "admin";

    public static final Event INVEST_EVENT = new Event("Invest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Crowdsale(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Crowdsale(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Crowdsale(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Crowdsale(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> cap() {
        final Function function = new Function(FUNC_CAP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> tokensSold() {
        final Function function = new Function(FUNC_TOKENSSOLD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> coinContract() {
        final Function function = new Function(FUNC_COINCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> startIco() {
        final Function function = new Function(
                FUNC_STARTICO, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> finalizeIco() {
        final Function function = new Function(
                FUNC_FINALIZEICO, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> remainingTokens() {
        final Function function = new Function(FUNC_REMAININGTOKENS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> basePrice() {
        final Function function = new Function(FUNC_BASEPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<Crowdsale> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _coinContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_coinContract)));
        return deployRemoteCall(Crowdsale.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Crowdsale> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _coinContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_coinContract)));
        return deployRemoteCall(Crowdsale.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Crowdsale> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _coinContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_coinContract)));
        return deployRemoteCall(Crowdsale.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Crowdsale> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _coinContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_coinContract)));
        return deployRemoteCall(Crowdsale.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<InvestEventResponse> getInvestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(INVEST_EVENT, transactionReceipt);
        ArrayList<InvestEventResponse> responses = new ArrayList<InvestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InvestEventResponse typedResponse = new InvestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.investor = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<InvestEventResponse> investEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, InvestEventResponse>() {
            @Override
            public InvestEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(INVEST_EVENT, log);
                InvestEventResponse typedResponse = new InvestEventResponse();
                typedResponse.log = log;
                typedResponse.investor = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<InvestEventResponse> investEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INVEST_EVENT));
        return investEventObservable(filter);
    }

    @Deprecated
    public static Crowdsale load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Crowdsale(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Crowdsale load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Crowdsale(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Crowdsale load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Crowdsale(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Crowdsale load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Crowdsale(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class InvestEventResponse {
        public Log log;

        public String investor;

        public BigInteger value;

        public BigInteger tokens;
    }
}
