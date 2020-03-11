pragma solidity >=0.5.0;

contract DDNS {

    address public owner;

    mapping(address => string) public records;
    mapping(string => address) public reverseRecords;

    modifier onlyOwner {
        require(msg.sender == owner);
        _;
    }

    constructor(string memory name) public {
        owner = msg.sender;
        records[msg.sender] = name;
        reverseRecords[name] = msg.sender;
        emit RegistrationEvent(now, msg.sender, name);
    }

    function register(address dest, string memory name) public onlyOwner {
        records[dest] = name;
        reverseRecords[name] = dest;
        emit RegistrationEvent(now, dest, name);
    }

    function query(address a) public view returns (string memory){
        return records[a];
    }

   function reverseQuery(string memory name) public view returns (address){
        return reverseRecords[name];
    }

    fallback  () external payable {
    }

    event RegistrationEvent(uint timeLog, address dest, string name);
        
}
