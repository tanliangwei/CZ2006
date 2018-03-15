# \[API\] Get public collection points
*Updated by Qu Zhe on 2018-03-15*
**Get public collection points with optional trash type query parameter.**


## URL

http://www.sjtume.cn/cz2006/api/get-public-points

## Mehod

`GET`
  
## URL Params
- **Required:**
  + `token=[string]` the access token 
    * currently set to static `9ca2218ae5c6f5166850cc749085fa6d` for simplicity

- **Optional:**
  + `point_type=[string]` the type of collection points to get
    * currently support `cash-for-trash` and `e-waste-recycling`

## Success Response
- **Status code:** 200
- **Content type:** JSON
- **Content:**
    ```
    { "count"   : (Integer) the number of returned collection points
      "content" : 
                [
                    { "id": 
                          (Integer) the collection point ID,
                      "latitude":   
                          (Double) the latitude of the point,
                      "longitude":  
                          (Double) the longtitude of the point,
                      "address_block_number":   
                          (String or null) the address block number,
                      "address_building_name":  
                          (String or null) the address building name,
                      "address_street_name": 
                          (String or null) the address street name,
                      "name": 
                          (String) the name of the point,
                      "description": 
                          (String or null) the description of the point,
                      "postal_code": 
                          (String or null) the postal code of the point,
                      "trash_type": 
                          (String) the acceptable trash type, can be cash-for-trash or e-waste-recycling
                    },
                    ... (collection point objects with same structure)
                ]
    }
    ```
 
## Error Response
### Unauthorized
- **Status code:** 401
- **Content type:** JSON
- **Content**: `{ error : 'invalid access token' }`

### Not found
- **Status code:** 404
- **Content type:** JSON
- **Content**: `{ error : "invalid point_type parameter" }`

## Sample Call
- Get all the public points:
  `curl http://www.sjtume.cn/cz2006/api/get-public-points?token=9ca2218ae5c6f5166850cc749085fa6d`
- Get "cash-for-trash" points:
  `curl http://www.sjtume.cn/cz2006/api/get-public-points?token=9ca2218ae5c6f5166850cc749085fa6d&point_type=cash-for-trash`


## Notes
- The access token is currently set to a static value for simplicity, which is a dangerous practice in terms of security. This problem will be fixed in future development.
- The opening hour of collection points is currently included in the `description` value. It will be parsed and returned seperately in future development.
