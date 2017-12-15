package gettingStarted.builders

def nodeBuilder = new NodeBuilder()
def userList = nodeBuilder.userList {
    user(id: '1', firstname: 'John', lastname: 'Smith') {
        address(type: 'home', street: '1 Main St.', city: 'Springfield', state: 'MA', zip: '12345')
        address(type: 'work', street: '2 South St.', city: 'Boston', state: 'MA', zip: '98765')
    }
    user(id: '2', firstname: 'Alice', lastname: 'Doe')
}

assert userList.user.@firstname.join(',') == 'John,Alice'
assert userList.user.find { it.@lastname == 'Smith' }.address.size() == 2