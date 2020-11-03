let res = [
    db.binding.drop(),
    db.binding.createIndex({varName: 1}, {unique: true}),
    db.binding.insert({varName: 'alpha', value: 20}),
]

printjson(res)
