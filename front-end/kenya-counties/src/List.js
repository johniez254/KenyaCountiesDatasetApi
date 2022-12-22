import React from 'react'

const List = ({ countiesData }) => {
  const [counties, setCounties] = React.useState(countiesData)
  return (
    <>
      <h3>{counties.length} total counties</h3>
      {counties.map((county) => {
        const {
          county_id,
          countyName,
          countyCode,
          totalConstituencies,
          totalWards,
        } = county
        return (
          <article className='person' key={county_id}>
            <div>
              <h4>
                {county_id}) {countyName}
              </h4>
              <h5>Code: {countyCode}</h5>
              <p>Constituencies: {totalConstituencies}</p>
              <p>Wards: {totalWards}</p>
            </div>
          </article>
        )
      })}
    </>
  )
}

export default List
