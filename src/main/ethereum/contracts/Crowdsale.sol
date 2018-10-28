pragma solidity ^0.4.25;

import "./CyBetToken.sol";


/**
 * @title Configurable
 * @dev Configurable varriables of the contract
 **/
contract Configurable {
    using SafeMath for uint256;
    uint256 public constant cap = 25000000*10**18;
    uint256 public constant basePrice = 10000*10**18; // tokens per 1 ether
    uint256 public tokensSold = 0;
    uint256 public remainingTokens = 0;
}
/**
 * @title Crowdsale
 * @dev Contract to preform crowd sale with token
 **/
contract Crowdsale is Configurable{
    /**
     * @dev enum of current crowd sale state
     **/
     address public admin;
     address private owner;
     CyBetToken public coinContract;
     enum Stages {
        none,
        icoStart,
        icoEnd
    }

    Stages currentStage;

    /**
     * @dev constructor of CrowdsaleToken
     **/
    constructor(CyBetToken _coinContract) public {
        admin = msg.sender;
        coinContract = _coinContract;
        owner = coinContract.owner();
        currentStage = Stages.none;
        remainingTokens = cap;
    }

    //Invest event
    event Invest(address investor, uint value, uint tokens);

    /**
     * @dev fallback function to send ether to for Crowd sale
     **/
    function () public payable {
        require(currentStage == Stages.icoStart);
        require(msg.value > 0);
        require(remainingTokens > 0);


        uint256 weiAmount = msg.value;// Calculate tokens to sell
        uint256 tokens = weiAmount.mul(basePrice).div(1 ether); // 1 token = 0.1 eth

        require(remainingTokens >= tokens);

        tokensSold = tokensSold.add(tokens); // Increment raised amount
        remainingTokens = cap.sub(tokensSold);

        coinContract.transfer(msg.sender, tokens);
        admin.transfer(weiAmount);// Send money to owner

        emit Invest(msg.sender, msg.value, tokens);
    }
    /**
     * @dev startIco starts the public ICO
     **/
    function startIco() external {
        require(msg.sender == admin);
        require(currentStage != Stages.icoEnd);
        currentStage = Stages.icoStart;
    }
    /**
     * @dev endIco closes down the ICO
     **/
    function endIco() internal {
        require(msg.sender == admin);
        currentStage = Stages.icoEnd;
        // transfer any remaining CyBet token balance in the contract to the owner
        coinContract.transfer(coinContract.owner(), coinContract.balanceOf(this));
    }
    /**
     * @dev finalizeIco closes down the ICO and sets needed varriables
     **/
    function finalizeIco() external {
        require(msg.sender == admin);
        require(currentStage != Stages.icoEnd);
        endIco();
    }
}
