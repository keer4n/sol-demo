pragma solidity >=0.5.0;

contract DDNS {

    address public owner;

    mapping(string => address) public records;
    mapping(address => string) public reverseRecords;

    modifier onlyOwner {
        require(msg.sender == owner);
        _;
    }

    constructor(string memory name) public {
        owner = msg.sender;
        records[name] = msg.sender;
        reverseRecords[msg.sender] = name;
        emit RegistrationEvent(now, name, msg.sender);
    }

    function register(string memory name, address dest) public onlyOwner {
        records[name] = dest;
        reverseRecords[dest] = name;
        emit RegistrationEvent(now, name, dest);
    }

    function reverseLookup(address a) public view returns (string memory){
        return reverseRecords[a];
    }

   function lookup(string memory name) public view returns (address){
        return records[name];
    }

   function () external payable {
    }

   function kill(address payable dest) public {
        selfdestruct(dest);
   }

    event RegistrationEvent(uint timeLog, string name, address dest);
        
}
