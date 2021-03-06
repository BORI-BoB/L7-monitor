import React from 'react'

import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import Typography from "@material-ui/core/Typography";

import componentStyles from "assets/theme/views/admin/dashboard.js";

import Field from "components/presenter/LogTable/Field";
import LogRow from "components/presenter/LogTable/LogRow";

const useStyles = makeStyles(componentStyles);

const LogTable = ({ logDatas }) => {

  const classes = useStyles();

  const subHeader = (
    <Grid container component={Box} alignItems="center" justifyContent="space-between" >
      <Grid item xs="auto">
        <Box
          component={Typography}
          variant="h3"
          marginBottom="0!important">
          24시간 내에 발생한 로그 확인하기
        </Box>
      </Grid>
    </Grid>
  );

  return (
    <Grid item xs={12} xl={4} component={Box} marginBottom="3rem!important" classes={{ root: classes.gridItemRoot }}>
      <Card classes={{ root: classes.cardRoot }}>
        <CardHeader
          subheader={subHeader}
          classes={{ root: classes.cardHeaderRoot }} />
        <TableContainer style={{ maxHeight: 300 }}>
          <Box
            component={Table}
            alignItems="center"
            marginBottom="0!important" >
            <TableHead>
              <Field />
            </TableHead>
            <TableBody>
              {logDatas.map((log, index) =>
                <LogRow key={index} data={log} />)
              }
            </TableBody>
          </Box>
        </TableContainer>
      </Card>
    </Grid>
  )
}

export default LogTable
