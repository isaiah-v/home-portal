import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import { SettingsButton } from './settings/SettingsButton';
import { BackgroundImage, Link } from './services/LinksService';
import { SearchBar } from './SearchBar';


const darkTheme = createTheme({
  palette: {
    mode: 'dark'
  }
});

const lightTheme = createTheme({
  palette: {
    mode: 'light',
    secondary: {
      main: '#EBEBEB'
    }
  }
});



export interface SearchAppBarProps {
  enableSettings: boolean,
  links: Link[],
  onLinksUpdated?: () => void,
  background: BackgroundImage | undefined,
  onSelectBackground?: (bg: BackgroundImage | undefined) => void
  onError?: (msg: string) => void
}

export default function SearchAppBar(props: SearchAppBarProps) {
  const isDark = window.matchMedia('(prefers-color-scheme: dark)').matches

  return (
    <Box sx={{ flexGrow: 1 }}>
      <ThemeProvider theme={isDark ? darkTheme : lightTheme}>
        <AppBar color='secondary' position="static">
          <Toolbar variant="dense">
            {props.enableSettings && <SettingsButton {...props} />}
            <Typography
              variant="h6"
              noWrap
              component="div"
              sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}
            >
              Home Portal
            </Typography>
            <SearchBar links={props.links}/>
          </Toolbar>
        </AppBar>
      </ThemeProvider>
    </Box>
  );
}
