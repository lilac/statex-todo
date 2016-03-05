/*
 * @flow weak
 * @author Junjun Deng
 */

export function sqlRowsToArray(rows: SQLResultSetRowList) {
  var res = [];
  for (let i = 0; i < rows.length; i++) {
    res.push(rows.item(i));
  }
  return res;
}