import * as React from 'react';

import SettingsIcon from '@mui/icons-material/Settings';
import { SettingsDialog } from './SettingsDialog'
import IconButton from '@mui/material/IconButton';

export interface SettingsButtonProps {
  onLinksUpdated?: () => void,
}

export const SettingsButton = (props: SettingsButtonProps) => {
  const [open, setOpen] = React.useState(false)
  
  const handleOpen = () => {
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
  }

  return (
    <>
      <IconButton
              onClick={handleOpen}
              size="large"
              edge="start"
              color="inherit"
              aria-label="open drawer"
              sx={{ mr: 2 }}
            >
              <SettingsIcon />
            </IconButton>
      <SettingsDialog 
        open={open}
        onClose={handleClose}
        onLinksUpdated={props.onLinksUpdated}
      />
    </>
  );
}