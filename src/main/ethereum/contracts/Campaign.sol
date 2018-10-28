pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

contract CampaignFactory {
    struct DeployedCampaign {
        string name;
        string description;
        address campaignAddress;
        string displayPictureUrl;
        string dateCreated;
    }

    uint public deployedCampaignsCount;
    mapping(address => bool) public existingDeployedCampaigns;
    DeployedCampaign[] public deployedCampaigns;

    function createCampaign(string name, string description, string displayPictureUrl, string dateCreated, uint minimum) public {
        address campaignAddress = new Campaign(minimum, msg.sender, name, description, displayPictureUrl, dateCreated);

        DeployedCampaign memory campaign = DeployedCampaign({
            name: name,
            description: description,
            campaignAddress: campaignAddress,
            displayPictureUrl: displayPictureUrl,
            dateCreated: dateCreated
        });

        deployedCampaigns.push(campaign);
        existingDeployedCampaigns[campaignAddress] = true;
        deployedCampaignsCount++;
    }

    function getDeployedCampaigns() public view returns (string[], string[], address[], string[], string[]) {
        string[] memory names = new string[](deployedCampaigns.length);
        string[] memory descriptions = new string[](deployedCampaigns.length);
        address[] memory addresses = new address[](deployedCampaigns.length);
        string[] memory displayPictureUrls = new string[](deployedCampaigns.length);
        string[] memory dateCreateds = new string[](deployedCampaigns.length);

        for (uint i = 0; i < deployedCampaigns.length; i++) {
            DeployedCampaign storage campaign = deployedCampaigns[i];
            names[i] = campaign.name;
            descriptions[i] = campaign.description;
            addresses[i] = campaign.campaignAddress;
            displayPictureUrls[i] = campaign.displayPictureUrl;
            dateCreateds[i] = campaign.dateCreated;
        }

        return (names, descriptions, addresses, displayPictureUrls, dateCreateds);
    }
}

contract Campaign {
    struct Request {
        string description;
        uint value;
        address recipient;
        bool complete;
        uint approvalCount;
        mapping(address => bool) approvals;
    }

    struct CampaignMetadata {
        string name;
        string description;
        string displayPictureUrl;
        string dateCreated;
    }

    Request[] public requests;
    CampaignMetadata public campaignMetadata;
    address public manager;
    uint public minimumContribution;
    mapping(address => bool) public approvers;
    uint public approversCount;

    modifier restricted() {
        require(msg.sender == manager, "Access to this function is restricted to managers only");
        _;
    }

    constructor(uint minimum, address creator, string name, string description,
                string displayPictureUrl, string dateCreated) public {
        manager = creator;
        minimumContribution = minimum;

        campaignMetadata = CampaignMetadata({
            name: name,
            description: description,
            displayPictureUrl: displayPictureUrl,
            dateCreated: dateCreated
        });
    }

    function contribute() public payable {
        require(msg.value > minimumContribution, "Contribution must meet minimum contribution");

        if(!approvers[msg.sender]) {
            approvers[msg.sender] = true;
            approversCount++;
        }
    }

    function createRequest(string description, uint value, address recipient) public restricted {
        Request memory newRequest = Request({
            description: description,
            value: value,
            recipient: recipient,
            complete: false,
            approvalCount: 0
        });

        requests.push(newRequest);
    }

    function approveRequest(uint index) public {
        Request storage request = requests[index];

        require(approvers[msg.sender], "Request approver must exist in approvers list");
        require(!request.approvals[msg.sender], "Approval is only done once");

        request.approvals[msg.sender] = true;
        request.approvalCount++;
    }

    function finalizeRequest(uint index) public restricted {
        Request storage request = requests[index];

        require(request.approvalCount > (approversCount / 2), "Approvals must be more than 50% of approvers");
        require(!request.complete, "Request is only completed");

        request.recipient.transfer(request.value);
        request.complete = true;
    }

    function getSummary() public view returns (
      uint, uint, uint, uint, address, string, string, string, string
      ) {
        return (
          minimumContribution,
          address(this).balance,
          requests.length,
          approversCount,
          manager,
          campaignMetadata.name,
          campaignMetadata.description,
          campaignMetadata.displayPictureUrl,
          campaignMetadata.dateCreated
        );
    }

    function getRequestsCount() public view returns (uint) {
        return requests.length;
    }
}